import {Component, Input} from "@angular/core";
import {Race} from "../../model/Race";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


@Component({
    selector: 'app-race-form',
    templateUrl: './race-form.component.html',
})

export class RaceFormComponent {

    @Input() race: Race;

    raceForm: FormGroup;
    raceFormSubmitted: Boolean;

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
            preRaceWaitingTimeSeconds: [race.preRaceWaitingTimeSeconds, [Validators.required, Validators.pattern(/^-?(30|[3-9]\d*)(\.\d+)?$/)]],
            sessionOverTimeSeconds: [race.sessionOverTimeSeconds, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)?$/)]],
            ambientTemp: [race.ambientTemp, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            cloudLevel: [race.cloudLevel, [Validators.required, Validators.min(0), Validators.max(1), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            rain: [race.rain, [Validators.required, Validators.min(0), Validators.max(1), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            weatherRandomness: [race.weatherRandomness, [Validators.required,  Validators.min(0), Validators.max(7), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            postQualySeconds: [race.postQualySeconds, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            postRaceSeconds: [race.postRaceSeconds, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            serverName: [race.serverName, [Validators.required]],
            sessionCount: [race.sessionDTOList !== null ?
                    race.sessionDTOList.length : 1, [Validators.required, Validators.min(1), Validators.pattern('[1-5]')]],
            startDate: [race.startDate, [Validators.required]]
        });
    }

}

