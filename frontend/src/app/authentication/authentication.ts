import { Injectable } from '@angular/core';

@Injectable()
export class AuthenticationInfo {

  LOCAL_STORAGE_TOKEN = 'APP_TOKEN';

  authorizationToken: string;

  refreshToken: string;

  expiresIn: number;

  constructor() {
  }

  load() {
    const strStorageToken: string = localStorage.getItem(this.LOCAL_STORAGE_TOKEN);
    if (strStorageToken != null) {
        const objStorageToken = JSON.parse(strStorageToken);
        this.authorizationToken = objStorageToken['authorizationToken'];
        this.refreshToken = objStorageToken['refreshToken'];
        this.expiresIn = objStorageToken['expiresIn'];
    }else {
      this.authorizationToken = '';
    }
  }

  save() {
    localStorage.setItem(this.LOCAL_STORAGE_TOKEN, JSON.stringify(this));
  }

  remove() {
    this.authorizationToken = null;
    this.refreshToken = null;
    localStorage.removeItem(this.LOCAL_STORAGE_TOKEN);
  }
}
