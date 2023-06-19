import {Component, ViewChild} from '@angular/core';

import {Router} from "@angular/router";
import {Championship} from "../../model/Championship";
import {ChampionshipService} from "../../service/championship.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {TranslateService} from "@ngx-translate/core";


@Component({
    selector: 'app-championship-form',
    templateUrl: './championship-form.component.html',
    styleUrls: ['./championship-form.component.css']
})

export class ChampionshipFormComponent {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    championship: Championship = new Championship();

    championshipForm: FormGroup;
    championshipFormSubmitted: Boolean;


    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService
    ) {}

    ngOnInit() {
        this.championship = new Championship;
        this.championshipFormBuilder();
    }

    onSubmit() {
        this.championshipFormSubmitted= true;
        if (this.championshipForm.valid) {
            this.championshipService.saveChampionship(this.championship).subscribe(response => {
                if (response) {
                    this.requestSuccessSwal.fire()
                }
            })
        }
    }

    championshipFormBuilder() {
            this.championshipFormSubmitted = false;
            this.championshipForm = this.formBuilder.group({
            name: ['', [Validators.required]],
            startDate: [null, [Validators.required]],
            description: ['', [Validators.required]],
            password: ['', [Validators.required]],
            spectatorPassword: ['', [Validators.required]],
            adminPassword: ['', [Validators.required]],
            trackMedalsRequirement: [null, [Validators.required, Validators.min(0), Validators.max(3)]],
            safetyRatingRequirement: [null, [Validators.required, Validators.min((-1)), Validators.max(99)]],
            racecraftRatingRequirement: [null, [Validators.required, Validators.min((-1)), Validators.max(99)]],
            carGroup: ['', [Validators.required]],
            maxCarSlots: [null, [Validators.required, Validators.min(1)]],
            dumpLeaderboards: [false, [Validators.required, Validators.min(0), Validators.max(1)]],
            isRaceLocked: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            randomizeTrackWhenEmpty: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            allowAutoDQ: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            shortFormationLap: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            dumpEntryList: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            ignorePrematureDisconnects: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            formationLapType: [null, [Validators.required]],
            centralEntryListPath: [''],
            imageContent: ['']
        })
    }
}

