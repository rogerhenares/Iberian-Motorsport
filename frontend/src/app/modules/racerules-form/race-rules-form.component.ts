import {Component, EventEmitter, Output, ViewChild} from '@angular/core';

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

    @Output() formSubmitted: EventEmitter<void> = new EventEmitter<void>();

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    raceRules: RaceRules = new RaceRules();

    raceRulesForm: FormGroup;
    raceRulesFormSubmitted: Boolean;

    constructor(
        private raceRulesService: RaceRulesService,
        public router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService
    ) {}

    ngOnInit() {
        this.raceRules = new RaceRules();
        this.raceRulesFormBuilder();
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

    raceRulesFormBuilder() {
        this.raceRulesFormSubmitted = false;
        this.raceRulesForm = this.formBuilder.group({
            qualifyStandingType: [null, [Validators.required, Validators.min(1), Validators.max(2)]],
            pitWindowLengthSec: [null, [Validators.required, Validators.min((-1))]],
            driverStintTimeSec: [null, [Validators.required, Validators.min((-1))]],
            mandatoryPitstopCount: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            maxTotalDrivingTime: [null, [Validators.required, Validators.min((-1))]],
            maxDriversCount: [null, [Validators.required, Validators.min(1)]],
            isRefuellingAllowedInRace: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            isRefuellingTimeFixed: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            isMandatoryPitstopRefuellingRequired: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            isMandatoryPitstopTyreChangeRequired: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            isMandatoryPitstopSwapDriverRequired: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
            tyreSetCount: [null, [Validators.required]]
        })
    }
}