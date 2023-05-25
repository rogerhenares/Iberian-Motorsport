import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';
import {AppContext} from "../../../util/AppContext";
import { TranslateService } from '@ngx-translate/core';
import {Championship} from "../../../model/Championship";
import {ChampionshipService} from "../../../service/championship.service";
import {Page} from "../../../model/Page";

@Component({
    selector: 'app-championship-list',
    templateUrl: './championship-list.component.html'
})
export class ChampionshipListComponent implements OnInit {

    // Swal Delete Championship
    @ViewChild('deleteChampionshipSwal', {static : true}) deleteChampionshipSwal: SwalComponent;
    @ViewChild('deleteFailSwal', {static : true}) deleteFailSwal: SwalComponent;
    @ViewChild('deleteSuccessSwal', {static : true}) deleteSuccessSwal: SwalComponent;

    championshipList: Championship[];
    currentPage: Page;

    championshipToDelete: Championship;

    constructor(
        public router: Router,
        private championshipService: ChampionshipService,
        private appContext: AppContext,
        private translateService: TranslateService
    ) {}

    ngOnInit() {
        this.currentPage = new Page;
        this.championshipList = [];
        this.getChampionshipList(this.currentPage.page);
    }

    getChampionshipList(page: Number) {
        this.championshipService.getChampionshipList(page).subscribe(
            response => {
                if (response) {
                    this.currentPage = response;
                    this.championshipList = response.data;
                }
            }
        );
    }

    goToChampionship(championship: Championship) {
        this.router.navigateByUrl('championship/' + championship.id);
    }

    goToNewChampionship() {
        this.router.navigateByUrl('championship/new');
    }

    delete(championship) {
        this.championshipToDelete = championship;
        this.deleteChampionshipSwal.title = this.translateService.instant('championship-delete-swal.title') + championship.name;
        this.deleteChampionshipSwal.fire();
    }

    deleteChampionshipConfirmed() {
        this.championshipService.deleteChampionship(this.championshipToDelete.id, this.deleteFailSwal).subscribe(
            response => {
                if (response) {
                    this.championshipToDelete = null;
                    this.deleteSuccessSwal.fire();
                    this.getChampionshipList(this.currentPage.page);
                }
            }
        );
    }
}