import {Component, Input, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {Race} from "../../model/Race";
import {AppContext} from "../../util/AppContext";
import {SanctionService} from "../../service/sanction.service";
import {Sanction} from "../../model/Sanction";

@Component({
    selector: 'app-sanctions',
    templateUrl: './sanctions.component.html',
    styleUrls: ['./sanctions.component.css']
})
export class SanctionsComponent implements OnInit, OnChanges {

    @Input() selectedRace: Race;

    sanctionsList: Array<Sanction>;

    constructor(
        private appContext: AppContext,
        private sanctionService: SanctionService
    ) {}

    ngOnInit(): void {
        this.loadSanctionList()
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes.selectedRace && !changes.selectedRace.firstChange) {
            this.loadSanctionList()
        }
    }


    loadSanctionList(): void {
        if (this.selectedRace.id) {
            this.sanctionService.getSanctionList(this.selectedRace.id).subscribe(
                (sanctionList) => {
                    this.sanctionsList = sanctionList;
                    console.log("Race id ->", this.selectedRace.id)
                    console.log("Response ->", sanctionList)
                    console.log("Sanction list ->", sanctionList)
                }
            )
        }
    }


    isSanctionForLoggedUser(sanction: Sanction): boolean {
        return sanction.grid.driversList.find(driver => this.appContext.isLoggedUser(driver)) !== undefined;
    }

}
