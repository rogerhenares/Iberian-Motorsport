import {Component, EventEmitter, Input, Output, ViewChild} from "@angular/core";
import {Grid} from "../../model/Grid";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SanctionService} from "../../service/sanction.service";
import {Sanction} from "../../model/Sanction";
import {GridRace} from "../../model/GridRace";
import {GridRaceService} from "../../service/gridrace.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-sanction-form',
    templateUrl: './sanction-form.component.html',
    styleUrls: ['./sanction-form.component.css']
})
export class SanctionFormComponent {

    @Output() formSubmitted: EventEmitter<void> = new EventEmitter<void>();

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    sanctionForm: FormGroup;
    sanctionFormSubmitted: Boolean;
    grid: Grid;
    championshipId: number;
    sanction: Sanction = new Sanction();
    gridRaceList: Array<GridRace> = [];
    isSendingRequest: boolean = false;

    constructor(
        private sanctionService: SanctionService,
        private formBuilder: FormBuilder,
        private gridRaceService: GridRaceService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.sanction.raceId = history.state.raceId;
        this.championshipId = history.state.championshipId;
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
        if (this.sanctionForm.valid && !this.isSendingRequest) {

            this.sanction.lap = this.sanctionForm.value.lap;
            this.sanction.penalty = this.sanctionForm.value.penalty;
            this.sanction.reason = this.sanctionForm.value.reason;
            this.sanction.gridId = this.sanctionForm.value.gridId;
            this.sanction.licensePoints = this.sanctionForm.value.licensePoints;
            this.sanction.inGame = this.sanctionForm.value.inGame;

            this.isSendingRequest = true

            this.sanctionService.createSanction(this.sanction).subscribe(response => {
                if (response) {
                    this.requestSuccessSwal.fire();
                    this.router.navigateByUrl("/championship/" + this.championshipId)
                    console.log("Sanction ->", this.sanction)
                }
                this.isSendingRequest = false;
            });
        }
    }



    loadGridRacesForRaceId(raceId: number) {
        this.gridRaceService.getGridRaceForRace(raceId).subscribe(
            (gridRaceList) => {
                this.gridRaceList = gridRaceList
            }
        )
    }

    sanctionFormBuilder(sanction: Sanction) {
        this.sanctionFormSubmitted = false;
        this.sanctionForm = this.formBuilder.group({
            lap: [sanction.lap, [Validators.required]],
            penalty: [sanction.penalty, [Validators.required]],
            reason: [sanction.reason, [Validators.required]],
            gridId: [sanction.gridId],
            raceId: [sanction.raceId],
            inGame: [sanction.inGame !== null ? sanction.inGame : 0],
            licensePoints: [sanction.licensePoints, [Validators.required]]
        })
    }

}
