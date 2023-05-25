import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';
import { TranslateService } from '@ngx-translate/core';
import {Championship} from "../../../model/Championship";
import {ChampionshipService} from "../../../service/championship.service";
import {AppContext} from "../../../util/AppContext";

@Component({
    selector: 'app-championship',
    templateUrl: './championship.component.html'
})
export class ChampionshipComponent implements OnInit {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    championship: Championship;

    championshipForm: FormGroup;
    championshipFormSubmitted: Boolean;

    constructor(
        private activeRoute: ActivatedRoute,
        private formBuilder: FormBuilder,
        private championshipService: ChampionshipService,
        private translate: TranslateService,
        private appContext: AppContext,
        public router: Router
    ) {}

    ngOnInit() {
        const routeParams = this.activeRoute.snapshot.params;
        this.championship = new Championship();
        this.getChampionship(routeParams.championshipId);
        this.championshipFormBuilder();
    }

    getChampionship(championshipId: Number) {
        if (championshipId) {
            this.championshipService.getChampionshipById(championshipId).subscribe(
                response => {
                    if (response) {
                        this.championship = response.data;
                        this.championshipFormBuilder();
                    }
                }
            );
        }
    }


    updateChampionship() {
        this.championshipFormSubmitted = true;
        if (this.championshipForm.valid) {
                this.championshipService.updateChampionship(this.championship, this.requestFailSwal).subscribe(
                    response => {
                        if (response) {
                            this.championshipUpdateFeedback(response.data);}});
                } else {
                    this.championshipService.updateChampionship(this.championship, this.requestFailSwal).subscribe(
                        response => {
                            if (response) {
                                this.championshipUpdateFeedback(response.data);
                            }
                        }
                    );
                }
            }


    championshipFormBuilder() {
        this.championshipFormSubmitted = false;
        this.championshipForm = null;
        this.championshipForm = this.formBuilder.group({
            name: ['', Validators.required],
            description: ['', Validators.required],
            adminPassword: ['', Validators.required],
            carGroup: ['', Validators.required],
            trackMedalsRequirement: [-1, Validators.required],
            safetyRatingRequirement: [-1, Validators.required],
            racecraftRatingRequirement: [-1, Validators.required],
            password: ['', Validators.required],
            spectatorPassword: ['', Validators.required],
            maxCarSlots: [-1, Validators.required],
            dumpLeaderboards: [-1, Validators.required],
            isRaceLocked: [-1, Validators.required]
        });
    }


    championshipUpdateFeedback(data: Championship) {
        this.requestSuccessSwal.title = this.translate.instant('championship-updated-swal.success.title');
        this.requestSuccessSwal.text = this.translate.instant('championship-updated-swal.success.text');
        this.requestSuccessSwal.fire();
        this.championship = data;
    }

}