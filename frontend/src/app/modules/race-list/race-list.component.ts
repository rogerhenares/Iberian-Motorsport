import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Race} from "../../model/Race";
import {Championship} from "../../model/Championship";
import {DatePipe} from "@angular/common";
import {TimezoneService} from "../../service/timezone.service";

@Component({
    selector: 'app-race-list',
    templateUrl: './race-list.component.html',
    styleUrls: ['./race-list.component.css']
})
export class RaceListComponent {

    @Input() selectedChampionship: Championship;
    @Input() selectedRace: Race;
    @Output() selected = new EventEmitter<Race>();
    selectedRaceId: number;

    constructor(
        private datePipe: DatePipe,
        private timezoneService: TimezoneService,
    ) {
    }

    ngOnInit() {
    }

    selectRace(race: Race) {
        this.selected.emit(race);
    }

    transformDate(date: string): string {
        let shortDate = this.datePipe.transform(new Date(date), 'M/d/yyyy', this.timezoneService.userTimezone);
        return shortDate;
    }

    formatDate(date: string): string {
        return this.transformDate(date);
    }

    getHour(date: string): string {
        return this.datePipe.transform(new Date(date), "HH:mm");
    }
}