import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AppContext} from "../../util/AppContext";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {



  constructor(public router: Router,
              public activatedRoute: ActivatedRoute,
              private userService: UserService,
              private appContext: AppContext
  ) { }

  ngOnInit() {
    console.log("decoded URI -> ", decodeURIComponent(this.router.url.split('?')[1]));
    this.appContext.authenticationInfo.authorizationToken = btoa(decodeURIComponent(this.router.url.split('?')[1]));
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
