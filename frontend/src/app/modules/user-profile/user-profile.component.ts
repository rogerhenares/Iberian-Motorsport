import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {AppContext} from "../../util/AppContext";
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import {User} from "../../model/User";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
  @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;

  user: User = new User();

  profileForm : FormGroup;

  constructor(public router: Router,
              public appContext: AppContext,
              private userService: UserService,
              private formBuilder: FormBuilder
  ) {}


  ngOnInit() {
    this.getData()
    console.log("init ->", this.user)
  }


  onSubmit(){
    if(this.profileForm.valid){
      this.updateUserRoles();
      console.log('submit ->', this.user)
      this.userService.updateUserInfo(this.user).subscribe(response => {
        if(response){
          this.requestSuccessSwal.fire()
        }
      });
    }
  }

  updateUserRoles() {
    this.user.roleList = [];
    if (this.profileForm.value.admin) {
      this.user.roleList.push('ADMIN');
    }
    if (this.profileForm.value.steward) {
      this.user.roleList.push('STEWARD');
    }
  }



  getData() {
    const navigation = this.router.getCurrentNavigation();
    if (history.state.user) {
      this.user = history.state.user;
      this.profileFormBuilder();
    } else {
      this.userService.getLoggedUser().subscribe(user => {
            if (user) {
              console.log("userService - getLoggedUser ->", this.user)
              this.user = user;
              this.profileFormBuilder();
            }
          });
    }
  }


  profileFormBuilder() {
    this.profileForm = null;
    this.profileForm = this.formBuilder.group({
      steamId: [this.user.steamId , Validators.required],
      firstName: [this.user.firstName, Validators.required],
      lastName: [this.user.lastName, Validators.required],
      shortName: [this.user.shortName, Validators.required],
      nationality: [this.user.nationality, Validators.required],
      admin: [this.user.roleList.includes('ADMIN')],
      steward: [this.user.roleList.includes('STEWARD')],
    });
  }

}
