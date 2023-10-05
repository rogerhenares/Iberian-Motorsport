import {Component, Input, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {Race} from "../../model/Race";
import {AppContext} from "../../util/AppContext";
import {SanctionService} from "../../service/sanction.service";
import {Sanction} from "../../model/Sanction";
import {Router} from "@angular/router";

@Component({
    selector: 'app-sanctions',
    templateUrl: './sanctions.component.html',
    styleUrls: ['./sanctions.component.css']
})
export class SanctionsComponent implements OnInit, OnChanges {

    @Input() selectedRace: Race;

    sanctionsList: Array<Sanction>;
    currentSanctionId: number;

    constructor(
        public appContext: AppContext,
        private sanctionService: SanctionService,
        public router: Router,
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
        if (this.selectedRace?.id) {
            this.sanctionService.getSanctionList(this.selectedRace.id).subscribe(
                (sanctionList) => {
                    this.sanctionsList = sanctionList;
                }
            )
        }
    }

    isSanctionForLoggedUser(sanction: Sanction): boolean {
        return sanction.grid.driversList.find(driver => this.appContext.isLoggedUser(driver)) !== undefined;
    }

    createNewSanction(raceId: number): void {
        this.router.navigateByUrl("sanction/new", {state: {raceId: raceId}});
    }

    setSanctionId(id: number) {
        this.currentSanctionId = id;
    }

    editSanction(raceId: number, gridId: number, sanction: Sanction): void {
        this.router.navigateByUrl("sanction/new", {state: {raceId: raceId, gridId: gridId, sanction: sanction}});
    }

    deleteSanction() {
       this.sanctionService.deleteSanction(this.currentSanctionId).subscribe(() => this.loadSanctionList())
    }
}


