import {Component, EventEmitter, Input, Output} from "@angular/core";
import {Championship} from "../../model/Championship";
import {ChampionshipService} from "../../service/championship.service";
import {Pageable} from "../../model/Pageable";
import {Race} from "../../model/Race";
import {CriteriaChampionship} from "../../model/CriteriaChampionship";
import {Router} from "@angular/router";
import {AppContext} from "../../util/AppContext";

@Component({
    selector: 'app-championship',
    templateUrl: './championship.component.html',
    styleUrls: ['./championship.component.css']
    })
export class ChampionshipComponent {
    @Input() criteria: CriteriaChampionship;
    @Input() isExpandible: boolean;
    @Input() isCreation: boolean;
    @Output() selected = new EventEmitter<Championship>();
    @Output() loadingChange = new EventEmitter<boolean>();

    pageable: Pageable = new Pageable(0, 3);
    championships: Championship[];
    selectedChampionship: Championship;
    totalPages: number = 0;
    selectedRace: Race = new Race();
    loading: boolean = false;

    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
        public appContext : AppContext
    ){}

    ngOnInit(): void {
        console.log("Criterial for champ home page -> {}", this.criteria );
        this.getChampionshipList(this.pageable.page, this.criteria);
    }

    getChampionshipList(page: number, criteria?: CriteriaChampionship) {
        this.loading = true;
        this.loadingChange.emit(this.loading)
        console.log("***Criterial for champ home page -> {}", this.criteria );
        if (!criteria) {
            this.pageable.page = page;
            this.championshipService.getChampionshipList(this.pageable).subscribe(
                (response: any) => {
                    this.loading = false;
                    this.loadingChange.emit(this.loading)
                    this.championships = response.content;
                    this.totalPages = response.totalPages.valueOf() - 1;
                    this.loadChampionshipNextRaceDate(this.championships);
                    if(!this.isCreation){
                        this.selectChampionship(this.championships[0])
                    }
                }
            )
        } else if (this.appContext.isAdmin()) {
            this.pageable.page = page;
            this.championshipService.getChampionshipByCriteriaAdmin(this.criteria, this.pageable).subscribe(
                (response: any) => {
                    this.loading = false;
                    this.loadingChange.emit(this.loading)
                    this.championships = response.content;
                    this.totalPages = response.totalPages.valueOf() - 1;
                    this.loadChampionshipNextRaceDate(this.championships);
                    if(!this.isCreation){
                        this.selectChampionship(this.championships[0])
                    }
                }
            )
        } else
            {
                this.pageable.page = page;
                this.championshipService.getChampionshipByCriteria(this.criteria, this.pageable).subscribe(
                    (response: any) => {
                        this.loading = false;
                        this.loadingChange.emit(this.loading)
                        this.championships = response.content;
                        this.totalPages = response.totalPages.valueOf() - 1;
                        this.loadChampionshipNextRaceDate(this.championships);
                    }
                )
            }
        }


    getClosestRace(championship: any): Race {
        const date = new Date();
        const lastRace = championship.raceList.at(-1);
        let filter = championship.raceList.filter(race => new Date(race.startDate) > date);
        return filter.length > 0 ? filter.pop() : lastRace;
    }


    loadChampionshipNextRaceDate(championshipList: Championship[]) : void {
        for (let championship of championshipList) {
            championship.nextRace = this.getClosestRace(championship);
        }
    }

    selectChampionship(championship: Championship) {
        this.selectedChampionship = championship;
        this.selected.emit(championship);
        console.log("Championship ->", championship)
    }

    goToPreviousPage() {
        if (this.pageable.page >= 1) {
            this.pageable.page = this.pageable.page.valueOf() - 1;
            this.getChampionshipList(this.pageable.page, this.criteria);
        }
    }

    goToNextPage() {
        if (this.pageable.page < this.totalPages) {
            this.pageable.page = this.pageable.page.valueOf() + 1;
            this.getChampionshipList(this.pageable.page, this.criteria);
        }
    }

    createNewChampionship() {
        this.router.navigateByUrl("championship/new");
    }

}
