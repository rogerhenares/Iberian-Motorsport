import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';

import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RaceRules} from "../../model/RaceRules";
import {RaceRulesService} from "../../service/racerules.service";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {TranslateService} from "@ngx-translate/core";

@Component({
    selector: 'app-race-rules-form',
    templateUrl: './race-rules-form.component.html'
})
export class RaceRulesFormComponent {

    @Input() raceRules: RaceRules;
    @Output() formSubmitted: EventEmitter<void> = new EventEmitter<void>();

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;


    raceRulesForm: FormGroup;
    raceRulesFormSubmitted: Boolean;

    constructor(
        private raceRulesService: RaceRulesService,
        public router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService
    ) {}

    ngOnInit() {
        this.raceRules !== undefined ?
            this.raceRulesFormBuilder(this.raceRules) :
            this.raceRulesFormBuilder(new RaceRules());
    }

    raceRulesSubmit() {
        this.raceRulesFormSubmitted= true;
        if (this.raceRulesForm.valid) {
            this.raceRulesService.saveRaceRules(this.raceRules).subscribe(response => {
                if (response) {
                    this.requestSuccessSwal.fire()
                }
            })
        }
    }

    raceRulesFormBuilder(raceRules: RaceRules) {
        this.raceRulesFormSubmitted = false;
        this.raceRulesForm = this.formBuilder.group({
            qualifyStandingType: [raceRules.qualifyStandingType, [Validators.required, Validators.min(1), Validators.max(2), Validators.pattern('[0-2]')]],
            pitWindowLengthSec: [raceRules.pitWindowLengthSec, [Validators.required, Validators.min((-1)), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            driverStintTimeSec: [raceRules.driverStintTimeSec, [Validators.required, Validators.min((-1)), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            mandatoryPitstopCount: [raceRules.mandatoryPitstopCount, [Validators.required, Validators.min(0), Validators.max(1), Validators.pattern('[0-1]')]],
            maxTotalDrivingTime: [raceRules.maxTotalDrivingTime, [Validators.required, Validators.min((-1)), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            maxDriversCount: [raceRules.maxDriversCount, [Validators.required, Validators.min(1), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            isRefuellingAllowedInRace: [raceRules.isRefuellingAllowedInRace || 0, [Validators.required, Validators.min(0), Validators.max(1)]],
            isRefuellingTimeFixed: [raceRules.isRefuellingTimeFixed || 0, [Validators.required, Validators.min(0), Validators.max(1)]],
            isMandatoryPitstopRefuellingRequired: [raceRules.isMandatoryPitstopRefuellingRequired || 0, [Validators.required, Validators.min(0), Validators.max(1)]],
            isMandatoryPitstopTyreChangeRequired: [raceRules.isMandatoryPitstopTyreChangeRequired || 0, [Validators.required, Validators.min(0), Validators.max(1)]],
            isMandatoryPitstopSwapDriverRequired: [raceRules.isMandatoryPitstopSwapDriverRequired || 0, [Validators.required, Validators.min(0), Validators.max(1)]],
            tyreSetCount: [raceRules.tyreSetCount, [Validators.required, Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]]
        })
    }
}