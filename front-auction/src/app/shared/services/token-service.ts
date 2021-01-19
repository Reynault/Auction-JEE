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
    localStorage.setItem(
      this._token,
      token.token
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
      token: localStorage.getItem(this._token)
    };
  }

  getUser(): string{
    return localStorage.getItem(this._username);
  }

  setUsername(user: string): void{
    localStorage.setItem(
      this._username,
      user
    );
  }

  del(): void{
    localStorage.removeItem(this._token);
    localStorage.removeItem(this._expiry);
    localStorage.removeItem(this._username);
  }
}
