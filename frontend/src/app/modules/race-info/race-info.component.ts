import {Component, Input, OnInit, ViewChild} from "@angular/core";
import {AppContext} from "../../util/AppContext";
import {ImportService} from "../../service/import.service";
import {ExportService} from "../../service/export.service";
import {Race} from "../../model/Race";
import { Clipboard } from '@angular/cdk/clipboard';
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {Championship} from "../../model/Championship";


@Component({
    selector: 'app-race-info',
    templateUrl: './race-info.component.html',
    styleUrls: ['./race-info.component.css']
})
export class RaceInfoComponent implements OnInit {
    @Input() selectedRace: any;
    @Input() selectedChampionship: any;
    @Input() maxHeight: number;
    @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
    @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

    maxTableHeight: number = 60;

    constructor(
        public appContext: AppContext,
        public importService: ImportService,
        public exportService: ExportService,
        public clipboard: Clipboard
    ){}

    ngOnInit() {
        if(this.maxHeight) {
            this.maxTableHeight = this.maxHeight - 2;
        }
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
                this.requestSuccessSwal.fire();
                console.log("Exported data");
                console.log(response);
            },
            error => {
                this.requestFailSwal.fire();
                console.log("Error exporting data");
                console.log(error);
            }
        );
    }


    importData(race: Race) {
        this.importService.importData(race).subscribe(
            response => {
                this.requestSuccessSwal.fire();
                console.log("Imported data");
            },
            error=> {
                this.requestFailSwal.fire();
                console.log("Error importing data");
                console.log(error)
            }
        );
    }

    canBeExported(race: Race) {
        return true;
        //return race.status === 'PENDING' || race.status === 'EXPORT_FAILED';
    }

    canBeImported(race: Race) {
        return true;
        //return race.status === 'LAUNCHED' || race.status === 'IMPORT_FAILED' || race.status === 'COMPLETED' || race.status === 'STOP';
    }

    copyToClipboard(championshipId: number, raceId: number) {
        let text: string = "C" + championshipId + "R" + raceId;
        this.clipboard.copy(text);
    }

    findRaceRound(selectedRace: Race, selectedChampionship: Championship): number {
        let raceRound = this.selectedChampionship.raceList.findIndex(race => race.id === selectedRace.id) + 1;
        return raceRound;
    }


}
