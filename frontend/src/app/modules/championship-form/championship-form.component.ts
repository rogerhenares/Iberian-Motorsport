import { Component } from '@angular/core';

import {Router} from "@angular/router";
import {Championship} from "../../model/Championship";
import {ChampionshipService} from "../../service/championship.service";

@Component({
    selector: 'app-championship-form',
    templateUrl: './championship-form.component.html',
    styleUrls: ['./championship-form.component.css']
})
export class ChampionshipFormComponent {
    private router: Router;

    championship: Championship = new Championship();

    constructor(private championshipService: ChampionshipService, router: Router) { }

    submit() {

        // Assign input values to championship object
        this.championship.name = (document.getElementById('name') as HTMLInputElement).value;
        this.championship.description = (document.getElementById('description') as HTMLInputElement).value;
        this.championship.password = (document.getElementById('password') as HTMLInputElement).value;
        this.championship.spectatorPassword = (document.getElementById('spectator-password') as HTMLInputElement).value;
        this.championship.adminPassword = (document.getElementById('admin-password') as HTMLInputElement).value;
        this.championship.trackMedalsRequirement = Number((document.getElementById('track-medals-requirement') as HTMLInputElement).value);
        this.championship.safetyRatingRequirement = Number((document.getElementById('safety-rating-requirement') as HTMLInputElement).value);
        this.championship.racecraftRatingRequirement = Number((document.getElementById('racecraft-rating-requirement') as HTMLInputElement).value);
        this.championship.carGroup = (document.getElementById('car-group') as HTMLInputElement).value;
        this.championship.maxCarSlots = Number((document.getElementById('max-car-slots') as HTMLInputElement).value);
        this.championship.dumpLeaderboards = Number((document.getElementById('dump-leaderboards') as HTMLInputElement).value);
        this.championship.isRaceLocked = Number((document.getElementById('is-race-locked') as HTMLInputElement).value);
        this.championship.randomizeTrackWhenEmpty = Number((document.getElementById('randomize-track-when-empty') as HTMLInputElement).value);
        this.championship.allowAutoDQ = Number((document.getElementById('allow-auto-dq') as HTMLInputElement).value);
        this.championship.shortFormationLap = Number((document.getElementById('short-formation-lap') as HTMLInputElement).value);
        this.championship.dumpEntryList = Number((document.getElementById('dump-entry-list') as HTMLInputElement).value);
        this.championship.formationLapType = Number((document.getElementById('formation-lap-type') as HTMLInputElement).value);
        this.championship.ignorePrematureDisconnects = Number((document.getElementById('ignore-premature-disconnects') as HTMLInputElement).value);
        this.championship.centralEntryListPath = (document.getElementById('central-entry-list-path') as HTMLInputElement).value;
        this.championship.imageContent = (document.getElementById('picture') as HTMLInputElement).value;

        // Call the service to save the championship object
        this.championshipService.saveChampionship(this.championship)
            .subscribe(() => {
                // Success callback
                // Redirect to the championship list page
                this.router.navigate(['/championship']);
            }, (error) => {
                // Error callback
                console.error(error);
                // Handle the error appropriately
            });
    }


}

