import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {Grid} from "../../model/Grid";
import {AppContext} from "../../util/AppContext";
import {GridService} from "../../service/grid.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CarService} from "../../service/car.service";
import {ChampionshipCategory} from "../../model/ChampionshipCategory";
import {Championship} from "../../model/Championship";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {response} from "express";


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

    constructor(
        private router: Router,
        public appContext: AppContext,
        private gridService: GridService,
        private formBuilder: FormBuilder,
        private carService: CarService,
    ) {
    }

    ngOnInit() {
        const navigation = this.router.getCurrentNavigation();
        this.championship = history.state.championship;
        this.grid = history.state.grid;
        console.log("join-champ -> ", this.championship);
        console.log("Grid ->", this.grid)
        this.gridFormBuilder();

    }

    gridFormBuilder() {
        this.gridSubmitted = false;
        this.gridForm = this.formBuilder.group({
            carNumber: [this.grid.carNumber, [Validators.required]],
            carLicense: [this.grid.carLicense],
            championshipId: [this.championship.id],
            driversList: [this.appContext.getLoggedUser()],
            car: [this.grid.car, [Validators.required]],
            teamName: [this.grid.teamName, [Validators.required]],
            disabled: [this.grid.disabled]
        })
    }


    join(){
        let grid: Grid = {
            id: this.grid.id,
            car: this.gridForm.value.car,
            carLicense: this.gridForm.value.carLicense,
            carNumber: this.gridForm.value.carNumber,
            championshipId: this.championship.id,
            teamName: this.gridForm.value.teamName,
            driversList: [this.appContext.getLoggedUser()],
            licensePoints: this.grid.licensePoints,
            points: this.grid.points,
            disabled: this.grid.disabled
        }
        if (history.state.grid) {
            this.gridService.updateGridEntry(grid).subscribe(
                response => {
                    if (response) {
                        this.requestSuccessSwal.fire()
                        this.router.navigateByUrl('/championship/' + this.championship.id)
                    }
                }
            )
        }
        else {
        this.gridService.createGridEntry(grid).subscribe(
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
}
