import {Component, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {ChampionshipService} from "../../service/championship.service";
import {Championship} from "../../model/Championship";
import {Router} from '@angular/router';
import {Race} from "../../model/Race";
import {AppContext} from "../../util/AppContext";
import {CriteriaChampionship} from "../../model/CriteriaChampionship";


@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

    selectedChampionship: Championship;
    selectedRace: Race;
    totalPages: number = 0;
    criteria: CriteriaChampionship = new CriteriaChampionship();

    constructor(
        private championshipService: ChampionshipService,
        public router: Router,
        public appContext : AppContext
    ) {}

    ngOnInit(): void {
    }


    onSelectedChampionship(selectedChampionship: Championship) {
        this.selectChampionship(selectedChampionship);
    }


    onSelectedRace(selectedRace: Race){
        this.selectedRace = selectedRace;
        this.selectTabInfo('standings')
    }


    selectChampionship(selectedChampionship: Championship) {
        this.selectedChampionship = selectedChampionship
        if (selectedChampionship.raceList?.length > 0) {
            this.selectedRace = selectedChampionship.nextRace ?
                selectedChampionship.nextRace :
                null;
        }
    }

    selectTabInfo(tabName: string){
        const elementTab = document.getElementById(tabName + 'Content');
        const elementHeader = document.getElementById(tabName + 'Header');
        const elementsHeader = document.querySelectorAll('[id*="Header"]');
        const elementsContent = document.querySelectorAll('[id*="Content"]');
        elementsHeader.forEach(header => {
            header.classList.remove('active');
        });
        elementsContent.forEach(content => {
            content.classList.remove('active');
        });
        elementHeader.classList.add('active');
        elementTab.classList.add('active');
    }

}

