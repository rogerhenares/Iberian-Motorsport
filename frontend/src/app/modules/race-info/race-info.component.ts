import {Component, Input, OnInit} from "@angular/core";
import {AppContext} from "../../util/AppContext";
import {ImportService} from "../../service/import.service";
import {ExportService} from "../../service/export.service";
import {Race} from "../../model/Race";

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
        public importService: ImportService,
        public exportService: ExportService
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

    exportData(race: Race) {
        this.exportService.exportData(race).subscribe(
            response => {
                console.log("Exported data");
                console.log(response);
            },
            error => {
                console.log("Error exporting data");
                console.log(error);
            }
        );
    }


    importData(race: Race) {
        this.importService.importData(race).subscribe(
            response => {
                console.log("Imported data");
                console.log(response);
            },
            error=> {
                console.log("Error importing data");
                console.log(error)
            }
        );
    }

    canBeExported(race: Race) {
        return race.status === 'PENDING' || race.status === 'EXPORT_FAILED';
    }

    canBeImported(race: Race) {
        return race.status === 'LAUNCHED' || race.status === 'IMPORT_FAILED' || race.status === 'COMPLETED';
    }

}
