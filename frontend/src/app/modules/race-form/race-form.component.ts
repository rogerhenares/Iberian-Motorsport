import {Component, EventEmitter, Output, ViewChild} from "@angular/core";

import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {Race} from "../../model/Race";
import {Form, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RaceService} from "../../service/race.service";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";


@Component({
    selector: 'app-race-form',
    templateUrl: './race-form.component.html',
})

export class RaceFormComponent {

    @Output() formSubmitted: EventEmitter<void> = new EventEmitter<void>();

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    race: Race = new Race();

    raceForm: FormGroup;
    raceFormSubmitted: Boolean;

    constructor(
        private raceService: RaceService,
        private router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService,

    ) {
    }

    ngOnInit() {
        this.race = new Race();
        this.raceFormBuilder();
    }

    raceSubmit() {
        this.raceFormSubmitted = true;
        if (this.raceForm.valid) {
            this.raceService.saveRace(this.race).subscribe(response =>{
                if (response) {
                    this.requestSuccessSwal.fire()
                }
            })
        }
    }

    raceFormBuilder() {
        this.raceFormSubmitted = false;
        this.raceForm = this.formBuilder.group({
            track: [null, [Validators.required]],
            preRaceWaitingTimeSeconds: [null, [Validators.required]],
            sessionOverTimeSeconds: [null, [Validators.required]],
            ambientTemp: [null, [Validators.required]],
            cloudLevel: [null, [Validators.required], Validators.min(0), Validators.max(1)],
            rain: [null, [Validators.required], Validators.min(0), Validators.max(1)],
            weatherRandomness: [null, [Validators.required,  Validators.min(0), Validators.max(7)]],
            postQualySeconds: [null, [Validators.required]],
            postRaceSeconds: [null, [Validators.required]],
            serverName: [null, [Validators.required]],
            sessionCount: [null, [Validators.required, Validators.min(1)]]
        })
    }
}