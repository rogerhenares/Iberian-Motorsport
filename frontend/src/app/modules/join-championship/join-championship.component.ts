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
        private carService: CarService
    ) {
    }

    ngOnInit() {
        const navigation = this.router.getCurrentNavigation();
        this.championship = history.state.championship;
        this.gridFormBuilder();
        console.log("join-champ -> ", this.championship);
    }

    gridFormBuilder() {
        this.gridSubmitted = false;
        this.gridForm = this.formBuilder.group({
            carNumber: [null, [Validators.required]],
            carLicense: [null],
            championshipId: [this.championship.id],
            driversList: [this.appContext.getLoggedUser()],
            car: [null, [Validators.required]],
            teamName: [null, [Validators.required]]
        })
    }


    join(){
        let grid: Grid = {
            id: null,
            carNumber: this.gridForm.value.carNumber,
            carLicense: null,
            championshipId: this.championship.id,
            teamName: this.gridForm.value.teamName,
            driversList: [this.appContext.getLoggedUser()],
            car: this.gridForm.value.car,
            points: 0,
        }
        console.log("Grid", grid)
        this.gridService.createGridEntry(grid).subscribe(
            response => {
                if (response) {
                    console.log("Response", response)
                    this.requestSuccessSwal.fire()
                }
            },
            error => {
                //show error if car number is already on use
                console.log("unable to create grid")
            }
        );
    }
}
