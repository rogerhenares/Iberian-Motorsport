import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
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
              private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.profileFormBuilder();
    console.log("init ->", this.user)
    this.getData()
  }

  onSubmit(){
    if(this.profileForm.valid){
      console.log('submit ->', this.user)
      this.userService.updateUserInfo(this.user).subscribe(response => {if(response){
        }
      });
    }
  }

  getData() {
    this.userService.getLoggedUser().subscribe(user => {
        if (user) {
          this.user = user;
        }
      }
    );
  }

  profileFormBuilder() {
    this.profileForm = null;
    this.profileForm = this.formBuilder.group({
      steamId: [ 0 , Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      shortName: ['', Validators.required],
      nationality: ['']
    });
  }
}
