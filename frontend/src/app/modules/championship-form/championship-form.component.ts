import {ChangeDetectorRef, Component, ViewChild} from '@angular/core';

import {Router} from "@angular/router";
import {Championship} from "../../model/Championship";
import {ChampionshipService} from "../../service/championship.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
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
    championshipCategory: ChampionshipCategory = new ChampionshipCategory();

    championshipForm: FormGroup;
    championshipFormSubmitted: Boolean;
    fileBase64: string;
    imageSrc: string = this.championship.imageContent;


    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
        private formBuilder: FormBuilder,
        private http: HttpClient,
        private cdr: ChangeDetectorRef
    ) {}


    ngOnInit() {
        const navigation = this.router.getCurrentNavigation();
        if (history.state.championship) {
            this.championship = history.state.championship;
            this.imageSrc = this.championship.imageContent;
            console.log(this.championship)
        }
        this.championshipFormBuilder();
    }

    onSubmit() {
        console.log("Flag 1")
        this.championshipFormSubmitted= true;
        console.log("To Submit ->", this.championshipForm.value);
        if (this.championshipForm.valid) {

            const champData = {...this.championshipForm.value};
            champData.id = this.championship.id

            console.log("Flag 2", champData)

            champData.startDate = this.prepareChampionshipDate(this.championshipForm.get('startDate').value);

            if (this.fileBase64) {
                champData.imageContent = this.fileBase64;
            }

            champData.isRaceLocked = Number(this.championshipForm.get('isRaceLocked').value)
            champData.randomizeTrackWhenEmpty = Number(this.championshipForm.get('randomizeTrackWhenEmpty').value)
            champData.shortFormationLap = Number(this.championshipForm.get('shortFormationLap').value)
            champData.ignorePrematureDisconnects = Number(this.championshipForm.get('ignorePrematureDisconnects').value)
            champData.dumpLeaderboards = Number(this.championshipForm.get('dumpLeaderboards').value)
            champData.allowAutoDq = Number(this.championshipForm.get('allowAutoDq').value)
            champData.dumpEntryList = Number(this.championshipForm.get('dumpEntryList').value)



            champData.raceList = new Array<Race>;
            champData.championshipCategoryList = new Array<ChampionshipCategory>;
            champData.carList = new Array<Car>;

            this.championshipService.saveChampionship(champData).subscribe(response => {
                if (response) {
                    this.requestSuccessSwal.fire()
                    this.router.navigateByUrl("championship");
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
                this.imageSrc = this.fileBase64;
                this.cdr.detectChanges(); // Force change detection
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
            style: [this.championship.style, [Validators.required]],
            description: [this.championship.description, [Validators.required]],
            password: [this.championship.password, [Validators.required]],
            spectatorPassword: [this.championship.spectatorPassword, [Validators.required]],
            adminPassword: [this.championship.adminPassword, [Validators.required]],
            trackMedalsRequirement: [this.championship.trackMedalsRequirement, [Validators.required, Validators.min(0), Validators.max(3)]],
            safetyRatingRequirement: [this.championship.safetyRatingRequirement, [Validators.required, Validators.min((-1)), Validators.max(99)]],
            racecraftRatingRequirement: [this.championship.racecraftRatingRequirement, [Validators.required, Validators.min((-1)), Validators.max(99)]],
            carGroup: [this.championship.carGroup, [Validators.required]],
            maxCarSlots: [this.championship.maxCarSlots, [Validators.required, Validators.min(1)]],
            subCarGroup: [this.championship.subCarGroup],
            maxSubCarSlots: [this.championship.maxSubCarSlots],
            isRaceLocked: [this.championship.isRaceLocked, [ Validators.min(0), Validators.max(1)]],
            randomizeTrackWhenEmpty: [this.championship.randomizeTrackWhenEmpty, [ Validators.min(0), Validators.max(1)]],
            allowAutoDq: [this.championship.allowAutoDq, [ Validators.min(0), Validators.max(1)]],
            shortFormationLap: [this.championship.shortFormationLap, [ Validators.min(0), Validators.max(1)]],
            ignorePrematureDisconnects: [this.championship.ignorePrematureDisconnects, [ Validators.min(0), Validators.max(1)]],
            formationLapType: [String(this.championship.formationLapType), [Validators.required]],
            centralEntryListPath: [this.championship.centralEntryListPath],
            imageContent: [this.championship.imageContent],
            dumpLeaderboards: [1, [Validators.min(0), Validators.max(1)]],
            dumpEntryList: [1, [ Validators.min(0), Validators.max(1)]],
            disabled: [this.championship.disabled, Validators.required],
            started: [this.championship.started, Validators.required],
            finished: [this.championship.finished, Validators.required]
        })
    }

    showPicker(): void {
        const datePicker = document.getElementById('datePicker') as any;
        if (datePicker && datePicker.showPicker) {
            datePicker.showPicker();
        }
    }

}

