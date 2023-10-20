import {Component, Input} from "@angular/core";
import {Race} from "../../model/Race";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DATE_LOCALE} from "@angular/material/core";
import {tracks} from "../../util/tracks";

@Component({
    selector: 'app-race-form',
    templateUrl: './race-form.component.html',
    styleUrls: ['./race-form.component.css'],
    providers: [
        {provide: MAT_DATE_LOCALE, useValue: 'en-GB'}
    ]
})

export class RaceFormComponent {

    @Input() race: Race;

    protected readonly tracks = tracks;

    raceForm: FormGroup;
    raceFormSubmitted: Boolean
    defaultSessionCount: number = 3;

    constructor(private formBuilder: FormBuilder) { }

    ngOnChanges() {
        this.race !== undefined ?
            this.raceFormBuilder(this.race) :
            this.raceFormBuilder(new Race());
    }

    raceFormBuilder(race: Race) {
        this.raceFormSubmitted = false;
        this.raceForm = this.formBuilder.group({
            track: [race.track, [Validators.required]],
            preRaceWaitingTimeSeconds: [race.preRaceWaitingTimeSeconds, [Validators.required, Validators.pattern(/^-?(30|[1-9]\d*)(\.\d+)?$/)]],
            sessionOverTimeSeconds: [race.sessionOverTimeSeconds, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)?$/)]],
            ambientTemp: [race.ambientTemp, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            cloudLevel: [race.cloudLevel, [Validators.required, Validators.min(0), Validators.max(1), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            rain: [race.rain, [Validators.required, Validators.min(0), Validators.max(1), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            weatherRandomness: [race.weatherRandomness, [Validators.required,  Validators.min(0), Validators.max(7), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            postQualySeconds: [race.postQualySeconds, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            postRaceSeconds: [race.postRaceSeconds, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            serverName: [race.serverName, [Validators.required]],
            sessionCount: [race.sessionDTOList !== null && race.sessionDTOList.length > 0 ? race.sessionDTOList.length : this.defaultSessionCount,
                [Validators.required, Validators.min(1), Validators.pattern('[1-5]')]],
            startDate: [race.startDate]
        });
    }

    showPicker(): void {
        const datePicker = document.getElementById('datePicker') as any;
        if (datePicker && datePicker.showPicker) {
            datePicker.showPicker();
        }
    }
}

