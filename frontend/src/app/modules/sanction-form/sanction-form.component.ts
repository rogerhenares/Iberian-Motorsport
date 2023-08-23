import {Component, EventEmitter, Input, Output, ViewChild} from "@angular/core";
import {Race} from "../../model/Race";
import {Grid} from "../../model/Grid";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SanctionService} from "../../service/sanction.service";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {Sanction} from "../../model/Sanction";
import {GridRace} from "../../model/GridRace";
import {GridRaceService} from "../../service/gridrace.service";

@Component({
    selector: 'app-sanction-form',
    templateUrl: './sanction-form.component.html'
})
export class SanctionFormComponent {

    @Output() formSubmitted: EventEmitter<void> = new EventEmitter<void>();

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    sanctionForm: FormGroup;
    sanctionFormSubmitted: Boolean;
    grid: Grid;
    sanction: Sanction = new Sanction();
    gridRaceList: Array<GridRace> = [];

    constructor(
        private sanctionService: SanctionService,
        private formBuilder: FormBuilder,
        private gridRaceService: GridRaceService
    ) {
    }

    ngOnInit() {
        this.sanction.gridId = history.state.gridId;
        this.sanction.raceId = history.state.raceId;
        if (this.sanction.raceId) {
            this.loadGridRacesForRaceId(this.sanction.raceId)
        }
        if (history.state.sanction) {
            this.sanction = history.state.sanction;
        }
        this.sanctionFormBuilder(this.sanction);
    }


    sanctionSubmit() {
        this.sanctionFormSubmitted = true;
        if (this.sanctionForm.valid) {
            this.sanctionService.createSanction(this.sanction).subscribe(response =>{
                if (response) {
                    this.requestSuccessSwal.fire();
                }
            })
        }}

    loadGridRacesForRaceId(raceId: number) {
        this.gridRaceService.getGridRaceForRace(raceId).subscribe(
            (gridRaceList) => {
                this.gridRaceList = gridRaceList
                console.log("GridRaceList ->", gridRaceList)
            }
        )
    }

    sanctionFormBuilder(sanction: Sanction) {
        this.sanctionFormSubmitted = false;
        this.sanctionForm = this.formBuilder.group({
            lap: [sanction.lap, [Validators.required]],
            penalty: [sanction.penalty, [Validators.required]],
            reason: [sanction.reason, [Validators.required]],
            gridId: [this.sanction.gridId, [Validators.required]],
            raceId: [this.sanction.raceId]
        })
    }

}
