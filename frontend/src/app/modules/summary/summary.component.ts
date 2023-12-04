import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AppContext} from "../../util/AppContext";


@Component({
    selector: 'app-summary',
    templateUrl: './summary.component.html',
    styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {
    constructor(
        public router: Router,
        public appContext : AppContext
    ) {}

    ngOnInit(): void {
    }
}

