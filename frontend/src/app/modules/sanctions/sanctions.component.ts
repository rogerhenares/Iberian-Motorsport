import {Component, Input, OnInit} from "@angular/core";
import {Grid} from "../../model/Grid";
import {Race} from "../../model/Race";
import {GridRace} from "../../model/GridRace";
import {GridRaceService} from "../../service/gridrace.service";

@Component({
    selector: 'app-sanctions',
    templateUrl: './sanctions.component.html',
    styleUrls: ['./sanctions.component.css']
})
export class SanctionsComponent implements OnInit {

    @Input() selectedRace: Race;
    @Input() selectedGrid: Grid;
    gridRace: GridRace = new GridRace();

    constructor(
        private gridRaceService: GridRaceService,
    ) {}

    ngOnInit(): void {
    }

    loadGridRace() {
        this.gridRaceService.getGridRace(this.selectedGrid.id, this.selectedRace.id).subscribe(
            (gridRaceData) => {
                this.gridRace = gridRaceData;
            },
            (error) => {
                console.error('Error fetching grid race data:', error);
            }
        );
    }

}