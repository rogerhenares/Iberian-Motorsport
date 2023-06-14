import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AppContext} from "../../util/AppContext";
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import {User} from "../../model/User";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User = new User();

  profileForm : FormGroup;

  constructor(public router: Router,
              public appContext: AppContext,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private activeRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    const routeParams = this.activeRoute.snapshot.params;
    this.profileFormBuilder();
    this.getData()
  }
  getData() {
    this.userService.getLoggedUser().subscribe(user => {
        if (user) {
          this.user.steamId= user.steamId
        }
      });
  }
  profileFormBuilder() {
    this.profileForm = null;
    this.profileForm = this.formBuilder.group({
      steamId: [ 0 , Validators.required]
      // firstName: ['', Validators.required],
      // lastName: ['', Validators.required],
      // shortName: ['', Validators.required],
      // nationality: ['']
    });

  }
}
