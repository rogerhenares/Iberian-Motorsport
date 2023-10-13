import { Injectable, EventEmitter } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { AuthenticationInfo } from '../authentication/authentication';
import { User } from '../model/User';

@Injectable()
export class AppContext extends EventEmitter<User> implements CanActivate {

    USER_KEY = 'LOGGED_USER';

    authenticationInfo: AuthenticationInfo;

    user: User;

    language: string;

    constructor(private router: Router) {
        super();
        this.authenticationInfo = new AuthenticationInfo();
        this.authenticationInfo.load();
        this.user = new User;
        this.load();
    }

    load() {
        const strStorageToken: string = localStorage.getItem(this.USER_KEY);
        if (strStorageToken != null) {
            const objStorageToken = JSON.parse(strStorageToken);
            this.setUser(objStorageToken);
        }
    }

    save() {
        localStorage.setItem(this.USER_KEY, JSON.stringify(this.user));
    }

    setUser(user: User) {
        this.user = user;
        this.save();
        this.emit(this.user);
    }

    clearUser() {
        this.user = new User();
        this.setUser(this.user);
        this.authenticationInfo.remove();
        localStorage.removeItem(this.USER_KEY);
        this.router.navigate(['/championship']);
    }

    getLoggedUser() {
        this.load();
        return this.user;
    }

    isAdmin() {
        return this.user.roleList.find(value => value === "ADMIN") !== undefined;
    }

    isSteward() {
        return this.user.roleList.find(value=> value === "STEWARD") !== undefined;
    }

    isUserLoggedToNavigate() : boolean {
        if(this.loadLoggedUser()) {
            console.log('user has credentials');
            return true;
        } else {
            console.log('user without credentials');
            this.router.navigate(['/championship']);
        }
    }

    isUserLoggedIn() : boolean {
        if(this.loadLoggedUser()) {
            console.log('user has credentials');
            return true;
        } else {
            console.log('user without credentials');
            return false;
        }
    }

    canActivate(): boolean {
        if (this.loadLoggedUser()) {
            console.log('Can activate passed');
            this.router.navigate(['/dashboard']);
            return true;
        } else {
            console.log('Can activate denied');
            this.router.navigate(['/championship']);
            return false;
        }
    }

    isLoggedUser(user: User) {
        return this.user.steamId === user.steamId;
    }

    loadLoggedUser():boolean {
        this.load();
        return this.user.steamId !== "";
    }

    isLoggedUserActive(){
        return this.user.steamId &&
            this.user.firstName &&
            this.user.lastName &&
            this.user.nationality &&
            this.user.shortName;
    }

}
