import {Component, EventEmitter, Input, Output, ViewChild} from "@angular/core";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {Race} from "../../model/Race";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RaceService} from "../../service/race.service";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {RaceRules} from "../../model/RaceRules";

@Component({
    selector: 'app-race-form',
    templateUrl: './race-form.component.html',
})

export class RaceFormComponent {

    @Input() race: Race;
    @Output() formSubmitted: EventEmitter<void> = new EventEmitter<void>();

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    raceForm: FormGroup;
    raceFormSubmitted: Boolean;

    constructor(
        private raceService: RaceService,
        private router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService,
    ) {
    }

    ngOnChanges() {
        this.race !== undefined ?
            this.raceFormBuilder(this.race) :
            this.raceFormBuilder(new Race());
    }


    raceSubmit() {
        this.raceFormSubmitted = true;
        if (this.raceForm.valid) {
            console.log("Race to save -> ", this.race);
            this.raceService.saveRace(this.race).subscribe(response =>{
                if (response) {
                    this.requestSuccessSwal.fire();
                }
            });
        }
    }

    raceFormBuilder(race: Race) {
        this.raceFormSubmitted = false;
        this.raceForm = this.formBuilder.group({
            track: [race.track, [Validators.required]],
            preRaceWaitingTimeSeconds: [race.preRaceWaitingTimeSeconds, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
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

