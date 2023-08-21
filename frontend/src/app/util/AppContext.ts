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
    }

    getLoggedUser() {
        this.load();
        return this.user;
    }

    isAdmin() {
        // console.log("isAdmin ->", this.user);
        return this.user.roleList.find(value => value === "ADMIN") !== undefined;
    }

    canActivate(): boolean {
        this.load();
        console.log('TCL: AppContext -> this.user', this.user);
        //if (this.user && Role.isAdmin(this.user.roleList) || Role.isUser(this.user.roleList)) {
            console.log('Can activate passed');
            return true;
        //} else {
        //    console.log('Can activate denied');
        //    this.router.navigate(['/login']);
        //    return false;
        //}
    }

    isLoggedUser(user: User) {
        return this.user.steamId === user.steamId;
    }

    isLoggedUserActive(){
        return this.user.steamId !== undefined &&
            this.user.firstName !== undefined &&
            this.user.lastName !== undefined &&
            this.user.nationality !== undefined &&
            this.user.shortName !== undefined;
    }
}
