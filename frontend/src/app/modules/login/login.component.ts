import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {AppContext} from "../../util/AppContext";
import {AuthenticationService} from "../../service/authentication.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

    steamParams = '';
    constructor(public router: Router,
                private authenticationService: AuthenticationService,
                private userService: UserService,
                private appContext: AppContext
    ) { }

    ngOnInit() {
        console.log('LoginComponent initialized')
        //let test2 = decodeURIComponent(this.router.url.split('?')[1]);
        this.steamParams = btoa(decodeURIComponent(this.router.url.replace("/login?", "")));
        this.authenticate();
    }

    authenticate() {
        this.authenticationService.authenticate(this.steamParams).subscribe(
            response => {
                if (response) {
                    console.log('authenticate -> ', response.token);
                    this.appContext.authenticationInfo.authorizationToken = response.token;
                    this.setLoggedUser();
                }
            });
    }

    setLoggedUser() {
        this.userService.getLoggedUser().subscribe(
            response => {
                if(response) {
                    this.appContext.setUser(response);
                    if(this.appContext.isLoggedUserActive()) {
                         this.router.navigateByUrl('/dashboard');
                    }else {
                        this.router.navigateByUrl('/user-profile');
                    }
                }
            }
        )
    }
}
