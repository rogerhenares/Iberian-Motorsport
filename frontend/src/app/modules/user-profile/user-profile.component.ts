import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {AppContext} from "../../util/AppContext";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {



  constructor(public router: Router,
              public appContext: AppContext,
              private userService: UserService
  ) { }

  ngOnInit() {
  }

  getData() {
    this.userService.getInfoDummy().subscribe(
    response => {
      if (response) {
        console.log("log test");
      }
    });
  }
}
