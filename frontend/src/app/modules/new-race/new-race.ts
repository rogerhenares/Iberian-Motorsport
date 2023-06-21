import {Component, ViewChild} from "@angular/core";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {RaceService} from "../../service/race.service";
import {SessionService} from "../../service/session.service";
import {RaceRulesService} from "../../service/racerules.service";
import {RaceFormComponent} from "../race-form/race-form.component";
import {SessionFormComponent} from "../session-form/session-form.component";
import {RaceRulesFormComponent} from "../racerules-form/race-rules-form.component";

@Component({
    selector: 'app-new-race',
    templateUrl: 'new-race.html'
})

export class NewRace {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;
    @ViewChild('raceFormComponent') raceFormComponent: RaceFormComponent;
    @ViewChild('raceRulesFormComponent') raceRulesFormComponent: RaceRulesFormComponent;
    @ViewChild('sessionFormComponent') sessionFormComponent: SessionFormComponent;


    raceForm: FormGroup;
    sessionForm: FormGroup;
    raceRulesForm: FormGroup;

    constructor(
        private raceService: RaceService,
        private sessionService: SessionService,
        private raceRulesService: RaceRulesService,
        private router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService
    ) {
        this.raceForm = this.formBuilder.group({})
        this.sessionForm = this.formBuilder.group({})
        this.raceRulesForm = this.formBuilder.group({})
    }

    ngOnInit() {

    }

    raceSubmit() {
        this.raceFormComponent.raceSubmit();
    }

    sessionSubmit() {
       this.sessionFormComponent.sessionSubmit();
    }

    raceRulesSubmit() {
        this.raceRulesFormComponent.raceRulesSubmit();
    }

}