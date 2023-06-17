import {Component, ViewChild} from '@angular/core';

import {Router} from "@angular/router";
import {Championship} from "../../model/Championship";
import {ChampionshipService} from "../../service/championship.service";
import {Form, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";

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

    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
        private formBuilder: FormBuilder
    ) {}

    ngOnInit() {
        this.championship = new Championship;
        this.championshipFormBuilder();
    }

    onSubmit() {
        console.log(this.championship)
        this.championshipService.saveChampionship(this.championship).subscribe()
    }

    championshipFormBuilder() {
            this.championshipForm = this.formBuilder.group({
            name: ['', [Validators.required]],
            startDate: ["2023-06-13T16:40", [Validators.required]],
            description: ['', [Validators.required]],
            password: ['', [Validators.required]],
            spectatorPassword: ['', [Validators.required]],
            adminPassword: ['', [Validators.required]],
            trackMedalsRequirement: [1, [Validators.required, Validators.min(0), Validators.max(3)]],
            safetyRatingRequirement: [1, [Validators.required, Validators.min(-1), Validators.max(99)]],
            racecraftRatingRequirement: [1, [Validators.required, Validators.min(-1), Validators.max(99)]],
            carGroup: ['', [Validators.required]],
            maxCarSlots: [30, [Validators.required, Validators.min(1)]],
            dumpLeaderboards: [1, [Validators.required, Validators.min(0), Validators.max(1)]],
            isRaceLocked: [1, [Validators.required, Validators.min(0), Validators.max(1)]],
            randomizeTrackWhenEmpty: [1, [Validators.required, Validators.min(0), Validators.max(1)]],
            allowAutoDQ: [0, [Validators.required, Validators.min(0), Validators.max(1)]],
            shortFormationLap: [0, [Validators.required, Validators.min(0), Validators.max(1)]],
            dumpEntryList: [0, [Validators.required, Validators.min(0), Validators.max(1)]],
            ignorePrematureDisconnects: [0, [Validators.required, Validators.min(0), Validators.max(1)]],
            formationLapType: [0, [Validators.required]],
            centralEntryListPath: [''],
            imageContent: ['']
        })
    }

}

