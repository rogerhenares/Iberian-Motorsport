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

@Component({
    selector: 'app-join-championship',
    templateUrl: './join-championship.component.html',
    styleUrls: ['./join-championship.component.css']
})
export class JoinChampionshipComponent implements OnInit {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

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
        private formBuilder: FormBuilder
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
        console.log("join-champ -> ", this.championship);
        console.log("Grid ->", this.grid)
        this.gridFormBuilder();
        this.categorizedCars.clear();
        this.groupCarsByCategory();
    }

    gridFormBuilder() {
        this.gridSubmitted = false;
        this.gridForm = this.formBuilder.group({
            carNumber: [this.teamSoloJoin === true? null : this.grid.carNumber, [Validators.required, Validators.pattern('^[0-9]{1,3}$')]],
            carLicense: [this.teamSoloJoin === true? null : this.grid.carLicense],
            championshipId: [this.championship.id],
            driversList: [this.teamSoloJoin === true? null : this.grid.driversList],
            car: [this.championship.carList.find(c => c.id === this.grid?.car?.id) , [Validators.required]],
            teamName: [this.grid.teamName, [Validators.required]],
            disabled: [this.grid.disabled]
        })
    }


    join(){
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
            managerId: this.grid.managerId,
            password: this.grid.password,
            disabled: this.grid.disabled
        }
        if (history.state.grid) {
            if (this.isTeamChampionship()){
                if(this.isGridManager() || this.appContext.isAdmin()) {
                    this.gridService.updateGridEntry(gridToSave).subscribe(
                        response => {
                            if (response) {
                                this.requestSuccessSwal.fire()
                                this.router.navigateByUrl('/championship/' + this.championship.id)
                            }
                        });
                }
                else {
                    this.gridService.addDriver(gridToSave, this.appContext.getLoggedUser().steamId, this.inputtedPassword).subscribe(
                    response => {
                        if (response) {
                            this.requestSuccessSwal.fire()
                            this.router.navigateByUrl('/championship/' + this.championship.id)
                        }
                    });
                }
            }
            else if (this.teamSoloJoin === true) {
                gridToSave.driversList = [this.appContext.getLoggedUser()]
                gridToSave.id = null
                gridToSave.licensePoints = 0
                gridToSave.points = 0
                gridToSave.password = null
                console.log("Grid to save ->", gridToSave)
                this.gridService.createGridEntry(gridToSave).subscribe(
                    response => {
                        if (response) {
                            this.requestSuccessSwal.fire()
                            this.router.navigateByUrl('/championship/' + this.championship.id)
                        }
                    }
                )
            }

            else {
                    console.log("Grid to update ->", this.grid)
                    this.gridService.updateGridEntry(gridToSave).subscribe(
                        response => {
                            if (response) {
                                this.requestSuccessSwal.fire()
                                this.router.navigateByUrl('/championship/' + this.championship.id)
                            }
                        }
                    )
                }
            }
        else {
            this.gridService.createGridEntry(gridToSave).subscribe(
                response => {
                    if (response) {
                        this.requestSuccessSwal.fire()
                        this.router.navigateByUrl('/dashboard');
                    }
                },
                error => {
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

    protected readonly history = history;
}
