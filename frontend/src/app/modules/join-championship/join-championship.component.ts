import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Grid} from "../../model/Grid";
import {AppContext} from "../../util/AppContext";
import {GridService} from "../../service/grid.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CarService} from "../../service/car.service";
import {ChampionshipCategoryService} from "../../service/championshipcategory.service";
import {Car} from "../../model/Car";
import {ChampionshipCategory} from "../../model/ChampionshipCategory";
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Subscription} from "rxjs";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";

@Component({
    selector: 'app-join-championship',
    templateUrl: './join-championship.component.html',
    styleUrls: ['./join-championship.component.css']
})
export class JoinChampionshipComponent implements OnInit {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    championshipId: number;
    grid: Grid = new Grid();
    gridForm: FormGroup;
    gridSubmitted: boolean;
    category: ChampionshipCategory;
    championship: Championship;

    constructor(
        private router: Router,
        private appContext: AppContext,
        private gridService: GridService,
        private formBuilder: FormBuilder,
        private carService: CarService,
        private championshipService: ChampionshipService
    ) {
    }

    ngOnInit() {
        const navigation = this.router.getCurrentNavigation();
        this.championshipId = history.state.championshipId;
        this.gridFormBuilder()
        this.getCarList()
    }

    gridFormBuilder() {
        this.gridSubmitted = false;
        this.gridForm = this.formBuilder.group({
            carNumber: [null, [Validators.required]],
            carLicense: [null],
            championshipId: [this.championshipId],
            driversList: [this.appContext.getLoggedUser()],
            car: [null, [Validators.required]],
            teamName: [null, [Validators.required]]
        })
    }



    getCarList() {
        this.championshipService.getChampionshipById(this.championshipId).subscribe(
            (championship: Championship) => {
                this.championship = championship;
                console.log('Championship:', championship);
            }
        );
    }


    join(){
        const grid: Grid = {
            id: null,
            carNumber: this.gridForm.value.carNumber,
            carLicense: null,
            carId: this.gridForm.value.car.id,
            championshipId: this.championshipId,
            driversList: [this.appContext.getLoggedUser()],
            car: this.gridForm.value.car,
            points: 0,
        }
        console.log("Grid", grid)
        if (this.gridForm.valid) {
            this.gridService.createGridEntry(grid).subscribe(response => {
                if (response) {
                    console.log("Grid saved successfully");
                    this.requestSuccessSwal.fire();
                }
            })
        }}
}
