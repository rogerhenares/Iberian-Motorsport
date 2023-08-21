import {Component, ViewChild} from "@angular/core";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {AbstractControl, FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {RaceService} from "../../service/race.service";
import {SessionService} from "../../service/session.service";
import {RaceRulesService} from "../../service/racerules.service";
import {RaceFormComponent} from "../race-form/race-form.component";
import {SessionFormComponent} from "../session-form/session-form.component";
import {RaceRulesFormComponent} from "../racerules-form/race-rules-form.component";
import {Race} from "../../model/Race";
import {RaceRules} from "../../model/RaceRules";
import {Session} from "../../model/Session";
import {MatStepper} from "@angular/material/stepper";

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
    @ViewChild('stepper') private stepper: MatStepper;


    raceForm: FormGroup;
    sessionForm: FormGroup;
    raceRulesForm: FormGroup;
    race: Race = new Race();
    sessionList: Session[] = new Array<Session>;
    raceRules: RaceRules= new RaceRules();
    previousSessionList: Session[];

    constructor(
        private raceService: RaceService,
        private sessionService: SessionService,
        private raceRulesService: RaceRulesService,
        private router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService,
        private route: ActivatedRoute
    ) {
        this.raceForm = this.formBuilder.group({})
        this.sessionForm = this.formBuilder.group({})
        this.raceRulesForm = this.formBuilder.group({})
    }

    ngOnInit() {
        const navigation = this.router.getCurrentNavigation();
        this.race.championshipId = history.state.championshipId
        if (history.state.race) {
            this.race = history.state.race;
            console.log("race =>", this.race)
            this.sessionList = history.state.race.sessionDTOList
            this.previousSessionList = history.state.race.sessionDTOList;
            console.log("session =>", this.sessionList)
            this.raceRules = history.state.race.raceRulesDTO
            console.log("raceRules =>", this.raceRules)
        }
    }

    onSubmit() {
        const raceData = this.raceFormComponent.raceForm.value;
        raceData.id = this.race.id
        raceData.championshipId = this.race.championshipId;
        const sessionData = this.sessionFormComponent.sessionForm.value;
        sessionData.raceId = raceData.id
        const raceRulesData = this.raceRulesFormComponent.raceRulesForm.value;
        raceRulesData.raceId = raceData.id
        console.log("Flag 1")
        if (this.raceRulesFormComponent.raceRulesForm.valid &&
            this.sessionFormComponent.sessionForm.valid &&
            this.raceRulesFormComponent.raceRulesForm.valid) {
            console.log("Flag 2")
            raceData.sessionDTOList = this.sessionList;
            raceData.raceRulesDTO = raceRulesData;
            console.log("PEPE ->", raceData);
            this.raceService.saveRace(raceData).subscribe(response => {
                 if (response) {
                    console.log("Flag 3")
                    this.requestSuccessSwal.fire();
                }
            });
        }
    }

    validateRace(): boolean {
        this.raceFormComponent.raceFormSubmitted = true;
        let isRaceValid = this.raceFormComponent.raceForm.valid;
        console.log("isRaceValid -> ", isRaceValid);
        if(isRaceValid == true) {
            this.updateSessionList();
            this.stepper.next();
        }
        return isRaceValid;
    }

    validateSession(){
        this.sessionFormComponent.sessionFormSubmitted = true;
        let isSessionValid: boolean = this.sessionFormComponent.sessionForm.valid;
        if(isSessionValid == true) {
            this.stepper.next();
        }
        return isSessionValid;
    }

    validateRaceRules() {
        this.raceRulesFormComponent.raceRulesFormSubmitted = true;
        let isRaceRulesValid: boolean = this.raceRulesFormComponent.raceRulesForm.valid;
        if (isRaceRulesValid == true) {
            this.stepper.next();
        }
        return isRaceRulesValid;
    }

    updateSessionList(): void{
        const raceData = this.raceFormComponent.raceForm.value;
        if(this.sessionList.length < raceData.sessionCount) {
            for (let i = this.sessionList.length ; i < raceData.sessionCount; i++)
                this.sessionList.push(new Session());
        }
        if(this.sessionList.length > raceData.sessionCount) {
            for (let i = this.sessionList.length - 1 ; i >= raceData.sessionCount; i--)
                this.sessionList.splice(i, 1);
        }
    }
}