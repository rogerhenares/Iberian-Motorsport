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

    invalidResponse= 'invalid';
    emptyParams = 'L2xvZ2lu';
    steamParams = '';
    loading: boolean = true;
    invalid: boolean = false;
    constructor(public router: Router,
                private authenticationService: AuthenticationService,
                private userService: UserService,
                private appContext: AppContext
    ) { }

    ngOnInit() {
        console.log('LoginComponent initialized')
        this.steamParams = btoa(decodeURIComponent(this.router.url.replace("/login?", "")));
        this.authenticate();
    }

    authenticate() {
        if(this.steamParams === this.emptyParams) {
            this.loading = false;
            return;
        }
        this.loading = true;
        this.authenticationService.authenticate(this.steamParams).subscribe(
            response => {
                if (response) {
                    console.log('authenticate -> ', response.token);
                    this.loading = false;
                    if(response.token === this.invalidResponse){
                        console.log("unable to validate steam token");
                        this.invalid = true;
                        return;
                    }
                    this.appContext.authenticationInfo.authorizationToken = response.token;
                    this.setLoggedUser();
                }
            },
            error => {
                this.loading = false;
                console.log('ERROR ON AUTHENTICATE: ', error)
            });
    }

    setLoggedUser() {
        this.userService.getLoggedUser().subscribe(
            response => {
                if(response) {
                    this.loading = false;
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

    steamLogging() {
        this.loading = true;
        this.authenticationService.getLoggingUrl().subscribe(
            response => {
                if(response) {
                    window.location.href = response.message;
                }
            },
            error => {
                console.log("unable to fetch steam URL");
            }
        )
    }
}
