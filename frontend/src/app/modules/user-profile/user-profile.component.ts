import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {AppContext} from "../../util/AppContext";
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import {User} from "../../model/User";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {AuthenticationService} from "../../service/authentication.service";
import {countries} from "../../util/countries";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  @ViewChild('requestFailSwal', {static : true}) requestFailSwal: SwalComponent;
  @ViewChild('requestSuccessSwal', {static : true}) requestSuccessSwal: SwalComponent;
  @ViewChild('profileNotCompletedSwal') private profileNotCompletedSwal: SwalComponent;


  protected readonly countries = countries;

  user: User = new User();

  profileForm : FormGroup;

  constructor(public router: Router,
              public appContext: AppContext,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private authService: AuthenticationService
  ) {}

  ngOnInit() {
    this.getData()
    console.log("init ->", this.user)
  }

  onSubmit(){
    if(this.profileForm.valid){
      this.updateUserRoles();
      console.log('submit ->', this.user)
      this.user.nationality = this.profileForm.value.nationality;
      this.user.firstName = this.profileForm.value.firstName;
      this.user.lastName = this.profileForm.value.lastName;
      this.user.shortName = this.profileForm.value.shortName;
      this.userService.updateUserInfo(this.user).subscribe(response => {
        if(response){
          if (this.appContext.getLoggedUser().steamId === response.steamId){
            this.appContext.setUser(response);
          }
          this.requestSuccessSwal.fire()
          this.router.navigateByUrl("dashboard")
        }
      });
    }
    else {
      this.profileNotCompletedSwal.fire();
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
              this.user = user;
              this.profileFormBuilder();
              console.log("userService - getLoggedUser ->", this.user)

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
      shortName: [this.user.shortName, [Validators.required, Validators.maxLength(3)]],
      nationality: [this.user.nationality, Validators.required],
      admin: [this.user.roleList.includes('ADMIN')],
      steward: [this.user.roleList.includes('STEWARD')],
    });
  }

  logout(user: User) {
      this.appContext.clearUser()
      this.authService.logout(Number(user.steamId))
      window.location.reload();
  }

}
