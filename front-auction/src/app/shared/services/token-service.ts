import {Injectable} from '@angular/core';
import {Token} from '../interfaces/token';

@Injectable({
  providedIn: 'root'
})
export class TokenService{

  private _token = 'access_token';
  private _expiry = 'expiry';
  private _username = 'username';

  set(token: Token): void{
    const now = new Date();
    localStorage.setItem(
      this._token,
      token.access_token
    );

    localStorage.setItem(
      this._expiry,
      (now.getTime() + token.expiry).toString()
    );

    localStorage.setItem(
      this._username,
      token.username
    );
  }

  hasToken(): boolean{
    const time: number = JSON.parse(localStorage.getItem(this._expiry));
    if (time === undefined || time < new Date().getTime()){
      this.del();
    }
    return !!localStorage.getItem(this._token);
  }

  get(): Token{
    return {
      access_token: localStorage.getItem(this._token),
      expiry: localStorage.getItem(this._expiry),
      username: localStorage.getItem(this._username)
    };
  }

  del(): void{
    localStorage.removeItem(this._token);
    localStorage.removeItem(this._expiry);
    localStorage.removeItem(this._username);
  }
}
