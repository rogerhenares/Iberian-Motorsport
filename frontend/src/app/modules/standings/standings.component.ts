import { Component, Input, OnInit } from '@angular/core';
import { Race } from '../../model/Race';
import { Grid } from '../../model/Grid';
import { GridService } from '../../service/grid.service';
import { Championship } from '../../model/Championship';
import { User } from '../../model/User';
import {UserService} from "../../service/user.service";

@Component({
    selector: 'app-standings',
    templateUrl: './standings.component.html',
    styleUrls: ['./standings.component.css']
})
export class StandingsComponent implements OnInit {
    @Input() selectedRace: Race;
    @Input() selectedChampionship: Championship;
    grid: Array<Grid>;
    userList: Array<User>;

    constructor(
        private gridService: GridService,
        private userService: UserService
    ) {}

    ngOnInit(): void {
        if (this.selectedRace && this.selectedChampionship.id) {
            this.gridService.getGridForChampionship(this.selectedChampionship.id).subscribe(
                (gridData) => {
                    this.grid = gridData;
                    console.log(this.grid);

                    // Retrieve user data for each steam_id and populate the userList
                    this.userList = [];

                    const steamIds = this.grid.flatMap((grid) => grid.driversList);

                    steamIds.forEach((steamId) => {
                        this.userService.getUserBySteamId(steamId).subscribe((user) => {
                            this.userList.push(user);
                        });
                    });

                    console.log(this.userList);
                },
                (error) => {
                    console.error('Error fetching grid data:', error);
                }
            );
        }
    }
}
