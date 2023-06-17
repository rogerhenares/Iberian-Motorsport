import {Component, ViewChild} from '@angular/core';

import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SessionService} from "../../service/session.service";
import {Session} from "../../model/Session";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {TranslateService} from "@ngx-translate/core";


@Component({
    selector: 'app-session-form',
    templateUrl: './session-form.component.html'
})
export class SessionFormComponent {

    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    sessionForm: FormGroup;
    sessionFormSubmitted: Boolean;
    session: Session = new Session();

    constructor(
        private sessionService: SessionService,
        public router: Router,
        private formBuilder: FormBuilder,
        private translate: TranslateService
    ) {}

    ngOnInit() {
        this.session = new Session();
        this.sessionFormBuilder();
    }

    onSubmit() {
        this.sessionFormSubmitted= true;
        if (this.sessionForm.valid) {
            this.sessionService.saveSession(this.session).subscribe(response => {
                if (response) {
                    this.requestSuccessSwal.fire()
                }
            })
        }
    }

    sessionFormBuilder() {
        this.sessionFormSubmitted = false;
        this.sessionForm = this.formBuilder.group({
            hourOfDay: [null, [Validators.required, Validators.min(0), Validators.max(23)]],
            dayOfWeekend: [null, [Validators.required, Validators.min(1), Validators.max(3)]],
            timeMultiplier: [null, [Validators.required, Validators.min(0), Validators.max(24)]],
            sessionType: [null, [Validators.required]],
            sessionDurationMinutes: [null, [Validators.required, Validators.min(0)]],
        })
    }
}