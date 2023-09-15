import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';

import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SessionService} from "../../service/session.service";
import {Session} from "../../model/Session";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {TranslateService} from "@ngx-translate/core";
import {Race} from "../../model/Race";


@Component({
    selector: 'app-session-form',
    templateUrl: './session-form.component.html'
})
export class SessionFormComponent {

    @Output() formSubmitted: EventEmitter<void> = new EventEmitter<void>();
    @Input() session: Session;

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    sessionForm: FormGroup;
    sessionFormSubmitted: Boolean;

    constructor(
        private sessionService: SessionService,
        public router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService
    ) {}

    ngOnInit() {
        this.session !== undefined ?
            this.sessionFormBuilder(this.session) :
            this.sessionFormBuilder(new Session());
    }

    sessionSubmit() {
        this.sessionFormSubmitted= true;
        if (this.sessionForm.valid) {
            this.sessionService.saveSession(this.session).subscribe(response => {
                if (response) {
                    this.requestSuccessSwal.fire()
                }
            })
        }
    }

    sessionFormBuilder(session: Session) {
        this.sessionFormSubmitted = false;
        this.sessionForm = this.formBuilder.group({
            hourOfDay: [session.hourOfDay, [Validators.required, Validators.min(0), Validators.max(23), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            dayOfWeekend: [session.dayOfWeekend, [Validators.required, Validators.min(1), Validators.max(3), Validators.pattern('[0-3]')]],
            timeMultiplier: [session.timeMultiplier, [Validators.required, Validators.min(0), Validators.max(24), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
            sessionType: [session.sessionType, [Validators.required, Validators.pattern(/^[qprQPR]$/)]],
            sessionDurationMinutes: [session.sessionDurationMinutes, [Validators.required, Validators.min(0), Validators.pattern(/^-?(0|[1-9]\d*)(\.\d+)?$/)]],
        })
    }
}