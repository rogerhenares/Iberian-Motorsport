import {Component, Input} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Session} from "../../model/Session";


@Component({
    selector: 'app-session-form',
    templateUrl: './session-form.component.html'
})
export class SessionFormComponent {

    @Input() session: Session;

    sessionForm: FormGroup;
    sessionFormSubmitted: Boolean;
    sessionTypeOptions: string[] = ['Q', 'P', 'R'];

    constructor(private formBuilder: FormBuilder) {}

    ngOnInit() {
        this.session !== undefined ?
            this.sessionFormBuilder(this.session) :
            this.sessionFormBuilder(new Session());
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