import {Component, Input, OnInit} from "@angular/core";
import {AppContext} from "../../util/AppContext";

@Component({
    selector: 'app-race-info',
    templateUrl: './race-info.component.html',
    styleUrls: ['./race-info.component.css']
})
export class RaceInfoComponent implements OnInit {
    @Input() selectedRace: any;
    @Input() selectedChampionship: any;

    constructor(
        public appContext: AppContext,
    ){}

    ngOnInit() {
    }


    getSessionType(type: string) {
        switch(type) {
            case 'Q':
                return 'Qualy';
            case 'P':
                return 'Practice';
            case 'R':
                return 'Race';
            default:
                return 'Unknown';
        }
    }

    getDayOfWeekend(type: number){
        switch (type) {
            case 1:
                return "Friday";
            case 2:
                return "Saturday";
            case 3:
                return "Sunday";
        }
    }

    getMandatoryPitstop(type: number) {
        switch(type) {
            case 0:
                return "Not required";
        }
    }

    getWeatherPercentage(number){
        return number * 100;
    }

}
