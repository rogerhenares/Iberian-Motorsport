import {Component, EventEmitter, Input, Output} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Bop} from "../../model/Bop";
import {Car} from "../../model/Car";

@Component({
    selector: 'app-bop-form',
    templateUrl: './bop-form.component.html',
    styleUrls: ['./bop-form.component.css']
    }
)
export class BopFormComponent {

    @Input() championshipCarList: Array<Car>;
    @Input() bop: Bop;

    bopForm: FormGroup;
    bopFormSubmitted: boolean

    constructor(
        private formBuilder: FormBuilder
    ) {
    }

    ngOnChanges() {
        this.bop !== undefined ?
            this.bopFormBuilder(this.bop) :
            this.bopFormBuilder(new Bop());
    }

    bopFormBuilder(bop: Bop) {
        this.bopFormSubmitted = false;
        this.bopForm = this.formBuilder.group({
            car: [this.bop.car, [Validators.required]],
            ballastKg: [this.bop.ballastKg,  [Validators.required]],
            restrictor: [this.bop.restrictor,  [Validators.required]]
        });
    }

}