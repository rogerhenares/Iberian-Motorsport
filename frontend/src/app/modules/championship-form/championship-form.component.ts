import {Component, ViewChild} from '@angular/core';

import {Router} from "@angular/router";
import {Championship} from "../../model/Championship";
import {ChampionshipService} from "../../service/championship.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {TranslateService} from "@ngx-translate/core";
import {Race} from "../../model/Race";
import {ChampionshipCategory} from "../../model/ChampionshipCategory";
import {Car} from "../../model/Car";
import {HttpClient} from "@angular/common/http";


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
    fileBase64: string;

    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
        private formBuilder: FormBuilder,
        private http: HttpClient
    ) {}


    ngOnInit() {
        const navigation = this.router.getCurrentNavigation();
        if (history.state.championship) {
            this.championship = history.state.championship;
        }
        this.championshipFormBuilder();
    }

    onSubmit() {
        this.championshipFormSubmitted= true;
        if (this.championshipForm.valid) {

            this.championship = {...this.championshipForm.value};
            this.championship.startDate = this.prepareChampionshipDate(this.championshipForm.get('startDate').value);
            this.championship.imageContent = this.fileBase64;
            this.championship.isRaceLocked = Number(this.championship.isRaceLocked)
            this.championship.randomizeTrackWhenEmpty = Number(this.championship.randomizeTrackWhenEmpty)
            this.championship.shortFormationLap = Number(this.championship.shortFormationLap)
            this.championship.ignorePrematureDisconnects = Number(this.championship.ignorePrematureDisconnects)
            this.championship.dumpLeaderboards = Number(this.championship.dumpLeaderboards)
            this.championship.allowAutoDq = Number(this.championship.allowAutoDq)
            this.championship.dumpEntryList = Number(this.championship.dumpEntryList)


            this.championship.raceList = new Array<Race>;
            this.championship.championshipCategoryList = new Array<ChampionshipCategory>;
            this.championship.carList = new Array<Car>;
            console.log("onSubmit ->", this.championship);
            this.championshipService.saveChampionship(this.championship).subscribe(response => {
                if (response) {
                    this.requestSuccessSwal.fire()
                    //this.router.navigateByUrl("championship");
                }
            })
        }
    }

    onFileSelected(event: any) {
        const selectedFile = event.target.files[0];

        if (selectedFile) {
            const reader = new FileReader();

            reader.onload = (e: any) => {
                this.fileBase64 = 'data:image/jpeg;base64,' + e.target.result.split(',')[1];
                console.log(this.fileBase64);
            };
            reader.readAsDataURL(selectedFile);
        }
    }

    prepareChampionshipDate(championshipDate: string) {
        championshipDate = championshipDate.replace('T', ' ')
        if (championshipDate.length < 19) {
            championshipDate = championshipDate + ':00';
        }
        return championshipDate;
    }

    championshipFormBuilder() {
            this.championshipFormSubmitted = false;

        this.championshipForm = this.formBuilder.group({
            name: [this.championship.name, [Validators.required]],
            startDate: [this.championship.startDate, [Validators.required]],
            description: [this.championship.description, [Validators.required]],
            password: [this.championship.password, [Validators.required]],
            spectatorPassword: [this.championship.spectatorPassword, [Validators.required]],
            adminPassword: [this.championship.adminPassword, [Validators.required]],
            trackMedalsRequirement: [this.championship.trackMedalsRequirement, [Validators.required, Validators.min(0), Validators.max(3)]],
            safetyRatingRequirement: [this.championship.safetyRatingRequirement, [Validators.required, Validators.min((-1)), Validators.max(99)]],
            racecraftRatingRequirement: [this.championship.racecraftRatingRequirement, [Validators.required, Validators.min((-1)), Validators.max(99)]],
            carGroup: [this.championship.carGroup, [Validators.required]],
            maxCarSlots: [this.championship.maxCarSlots, [Validators.required, Validators.min(1)]],
            isRaceLocked: [this.championship.isRaceLocked, [ Validators.min(0), Validators.max(1)]],
            randomizeTrackWhenEmpty: [this.championship.randomizeTrackWhenEmpty, [ Validators.min(0), Validators.max(1)]],
            allowAutoDq: [this.championship.allowAutoDq, [ Validators.min(0), Validators.max(1)]],
            shortFormationLap: [this.championship.shortFormationLap, [ Validators.min(0), Validators.max(1)]],
            ignorePrematureDisconnects: [this.championship.ignorePrematureDisconnects, [ Validators.min(0), Validators.max(1)]],
            formationLapType: [this.championship.formationLapType, [Validators.required]],
            centralEntryListPath: [this.championship.centralEntryListPath],
            imageContent: [""],
            dumpLeaderboards: [1, [Validators.min(0), Validators.max(1)]],
            dumpEntryList: [1, [ Validators.min(0), Validators.max(1)]],
            disabled: [1, Validators.required],
            started: [0, Validators.required],
            finished: [0, Validators.required]
        })
    }

}

