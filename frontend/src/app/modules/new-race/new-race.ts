import {Component, QueryList, ViewChild, ViewChildren} from "@angular/core";
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
    @ViewChild('stepper') private stepper: MatStepper;

    @ViewChildren('sessionFormComponent') sessionFormComponents: QueryList<SessionFormComponent>;


    race: Race = new Race();
    sessionList: Session[] = new Array<Session>;
    raceRules: RaceRules= new RaceRules();
    previousSessionList: Session[];

    constructor(
        private raceService: RaceService,
        private router: Router
    ) { }

    ngOnInit() {
        const navigation = this.router.getCurrentNavigation();
        this.race.championshipId = history.state.championshipId
        if (history.state.race) {
            this.race = history.state.race;
            this.sessionList = history.state.race.sessionDTOList
            this.previousSessionList = history.state.race.sessionDTOList;
            this.raceRules = history.state.race.raceRulesDTO
        }
    }

    onSubmit() {
        const raceData = this.raceFormComponent.raceForm.value;
        raceData.id = this.race.id
        raceData.championshipId = this.race.championshipId;
        const sessionData = this.sessionList;
        for (let session of sessionData) {
            session.raceId = raceData.id
        }
        const raceRulesData = this.raceRulesFormComponent.raceRulesForm.value;
        raceRulesData.raceId = raceData.id
        if (this.raceFormComponent.raceForm.valid &&
            this.raceRulesFormComponent.raceRulesForm.valid) {

            let raceDate = this.raceFormComponent.raceForm.get('startDate').value;
            raceData.startDate = this.prepareRaceDate(raceDate);

            raceData.sessionDTOList = this.sessionList;
            raceData.raceRulesDTO = this.prepareRaceRules(raceRulesData);

            this.raceService.saveRace(raceData).subscribe(response => {
                if (response) {
                    this.requestSuccessSwal.fire();
                    this.router.navigateByUrl("/championship/" + this.race.championshipId);
                }
            });
        }
    }

    validateRace(): boolean {
        this.raceFormComponent.raceFormSubmitted = true;
        let isRaceValid = this.raceFormComponent.raceForm.valid;
        if(isRaceValid == true) {
            this.updateSessionList();
            this.stepper.next();
        }
        return isRaceValid;
    }

    validateSession(index: number){
        const sessionFormComponent = this.sessionFormComponents.toArray()[index];
        sessionFormComponent.sessionFormSubmitted = true;
        let isSessionValid: boolean = sessionFormComponent.sessionForm.valid;
        if(isSessionValid == true) {
            this.sessionList[index] = sessionFormComponent.sessionForm.value;
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

    prepareRaceRules(raceRulesData: RaceRules) {
        raceRulesData.isRefuellingAllowedInRace = Number(raceRulesData.isRefuellingAllowedInRace)
        raceRulesData.isRefuellingTimeFixed = Number(raceRulesData.isRefuellingTimeFixed)
        raceRulesData.isMandatoryPitstopRefuellingRequired = Number(raceRulesData.isMandatoryPitstopRefuellingRequired)
        raceRulesData.isMandatoryPitstopTyreChangeRequired = Number(raceRulesData.isMandatoryPitstopTyreChangeRequired)
        raceRulesData.isMandatoryPitstopSwapDriverRequired = Number(raceRulesData.isMandatoryPitstopSwapDriverRequired)
        return raceRulesData;
    }

    prepareRaceDate(raceDate: string) {
        raceDate = raceDate.replace('T', ' ')
        if (raceDate.length < 19) {
            raceDate = raceDate + ':00';
        }
        return raceDate;
    }

}