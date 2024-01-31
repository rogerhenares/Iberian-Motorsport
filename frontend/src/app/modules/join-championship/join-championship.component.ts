import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {Grid} from "../../model/Grid";
import {AppContext} from "../../util/AppContext";
import {GridService} from "../../service/grid.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ChampionshipCategory} from "../../model/ChampionshipCategory";
import {Championship} from "../../model/Championship";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {Car} from "../../model/Car";
import Swal from "sweetalert2";
import {User} from "../../model/User";
import {Clipboard} from "@angular/cdk/clipboard";

@Component({
    selector: 'app-join-championship',
    templateUrl: './join-championship.component.html',
    styleUrls: ['./join-championship.component.css']
})
export class JoinChampionshipComponent implements OnInit {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;
    @ViewChild('errorSwal', {static : true}) errorSwal: SwalComponent;

    grid: Grid = new Grid();
    gridForm: FormGroup;
    gridSubmitted: boolean;
    category: ChampionshipCategory;
    championship: Championship;
    teamSoloJoin: boolean = false;
    inputtedPassword: string;
    categorizedCars = new Map<string, Car[]>();

    constructor(
        private router: Router,
        public appContext: AppContext,
        private gridService: GridService,
        private formBuilder: FormBuilder,
        public clipboard: Clipboard
    ) {
    }
    ngOnInit() {
        const navigation = this.router.getCurrentNavigation();
        this.championship = history.state.championship;
        if (history.state.grid) {
            this.grid = history.state.grid;
            this.teamSoloJoin = history.state.teamSoloJoin;
            this.inputtedPassword = history.state.password;
        }
        this.gridFormBuilder();
        this.categorizedCars.clear();
        this.groupCarsByCategory();
        if(this.inputtedPassword) {
            this.grid.password = this.inputtedPassword;
        }
    }

    gridFormBuilder() {
        this.gridSubmitted = false;
        this.gridForm = this.formBuilder.group({
            carNumber: [this.teamSoloJoin === true? null : this.grid.carNumber, [Validators.required, Validators.pattern('^[0-9]{1,3}$')]],
            carLicense: [this.teamSoloJoin === true? "PRO" : this.grid.carLicense],
            championshipId: [this.championship.id],
            driversList: [this.teamSoloJoin === true? null : this.grid.driversList],
            car: [this.championship.carList.find(c => c.id === this.grid?.car?.id) , [Validators.required]],
            teamName: [this.grid.teamName, [Validators.required, Validators.pattern('^[a-zA-Z0-9][a-zA-Z0-9\\s]*$')]],
            disabled: [this.grid.disabled]
        })
    }

    join(){
        if(this.gridForm.invalid) {
            return;
        }
        if (!this.grid.driversList.some(driver => driver.steamId === this.appContext.getLoggedUser().steamId)) {
            let newUser = {...this.appContext.getLoggedUser()};
            this.grid.driversList.push(newUser);
        }
        let gridToSave: Grid = {
            id: this.grid.id,
            car: this.gridForm.value.car,
            carLicense: this.gridForm.value.carLicense,
            carNumber: this.gridForm.value.carNumber,
            championshipId: this.championship.id,
            teamName: this.gridForm.value.teamName,
            driversList: this.grid.driversList,
            licensePoints: this.grid.licensePoints,
            points: this.grid.points,
            pointsDrop: this.grid.pointsDrop,
            managerId: this.grid.managerId,
            password: this.grid.password,
            disabled: this.grid.disabled,
            newManagerId: this.grid.newManagerId
        }
        if (history.state.grid) {
            if (this.isTeamChampionship()){
                if(this.isGridManager() || this.appContext.isAdmin()) {
                    this.gridService.updateGridEntry(gridToSave, this.errorSwal).subscribe(
                        response => {
                            if (response) {
                                this.requestSuccessSwal.fire()
                                this.router.navigateByUrl('/championship/' + this.championship.id)
                            }
                        });
                }
                else {
                    this.gridService.addDriver(gridToSave, this.appContext.getLoggedUser().steamId, this.inputtedPassword, this.errorSwal).subscribe(
                    response => {
                        if (response) {
                            this.requestSuccessSwal.fire()
                            this.router.navigateByUrl('/championship/' + this.championship.id)
                        } else {
                            this.grid.driversList.pop();
                        }
                    });
                }
            }
            else if (this.teamSoloJoin === true) {
                gridToSave.driversList = [this.appContext.getLoggedUser()]
                gridToSave.id = null
                gridToSave.licensePoints = 0
                gridToSave.points = 0
                this.gridService.createGridEntry(gridToSave, this.errorSwal).subscribe(
                    response => {
                        if (response) {
                            this.requestSuccessSwal.fire()
                            this.router.navigateByUrl('/championship/' + this.championship.id)
                        } else {
                            this.grid.driversList.pop();
                        }
                    }
                )
            }

            else {
                    this.gridService.updateGridEntry(gridToSave, this.errorSwal).subscribe(
                        response => {
                            if (response) {
                                this.requestSuccessSwal.fire()
                                this.router.navigateByUrl('/championship/' + this.championship.id)
                            } else {
                                this.grid.driversList.pop();
                            }
                        }
                    )
                }
            }
        else {
            this.gridService.createGridEntry(gridToSave, this.errorSwal).subscribe(
                response => {
                    if (response) {
                        this.requestSuccessSwal.fire()
                        this.router.navigateByUrl('/dashboard');
                    } else {
                        this.grid.driversList.pop();
                    }
                });
            }
        }

    groupCarsByCategory() {
        this.championship.carList.forEach(car => {
            const category = car.category;
            if (this.categorizedCars.has(category)) {
                this.categorizedCars.get(category)?.push(car);
            } else {
                this.categorizedCars.set(category, [car]);
            }
        });
    }

    isSoloChampionship() {
        return this.championship.style === 'SOLO';
    }

    isTeamChampionship() {
        return this.championship.style === 'TEAM';
    }

    isNewGrid() {
        return this.grid.id === -1;
    }

    isGridManager() {
        return this.grid.managerId === this.appContext.getLoggedUser().userId;
    }

    isDriver() {
        return this.grid.driversList.find(driver => driver.steamId === this.appContext.getLoggedUser().steamId) != undefined;
    }

    giveManager(event, user: User) {
        event.preventDefault();

        Swal.fire({
            title: 'Are you sure?',
            text: "You are about to assign a new manager. Do you want to proceed?",
            background: '#151515',
            showCancelButton: true,
            confirmButtonColor: '#00EBB1',
            cancelButtonColor: '#e91e63',
            confirmButtonText: 'Yes, assign!'
        }).then((result) => {
            if (result.value) {
                this.grid.newManagerId = user.userId;
                this.gridService.updateGridEntry(this.grid).subscribe(
                    response => {
                        if(response) {
                            this.router.navigateByUrl('/championship/' + this.championship.id)
                        }
                    }
                )
            }
        })
    }

    copyToClipboard(password: string) {
        console.log("copyToClipboard -> {}" ,password);
        this.clipboard.copy(password);
    }

    protected readonly history = history;
}
