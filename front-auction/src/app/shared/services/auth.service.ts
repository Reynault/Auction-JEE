import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {User} from '../interfaces/user';
import {Token} from '../interfaces/token';
import {Observable} from 'rxjs';
import {TokenService} from './token-service';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly _backendURL: any;
  private readonly _defaultUser: User;

  constructor(private _http: HttpClient,
              private _token: TokenService,
              private router: Router) {
    this._defaultUser = {
      username: 'John',
      firstname: 'Joe',
      lastname: 'Mama',
      password: 'Doe',
      address: 'null'
    };
    this._backendURL = {};

    // build backend base url
    let baseUrl = `${environment.backend.protocol}://${environment.backend.host}`;
    if (environment.backend.port) {
      baseUrl += `:${environment.backend.port}`;
    }

    // build all backend urls
    Object.keys(environment.backend.endpoints).forEach(k => this._backendURL[k] = `${baseUrl}${environment.backend.endpoints[k]}`);
  }

  /**
   * Returns private property _defaultPerson
   */
  get defaultUser(): User {
    return this._defaultUser;
  }

  connect(user: User): Observable<any> {
    return this._http.post<Token>(this._backendURL.login, user).pipe(
      map(_ => this._token.set(_)),
    );
  }

  subscribe(user: User): Observable<any> {
    return this._http.post<Token>(this._backendURL.register, user).pipe(
      map(_ => this._token.set(_)),
    );
  }

  delete(): Observable<any> {
    return this._http.delete(this._backendURL.profile).pipe(
      map(_ => this._token.del())
    );
  }

  modify(_user: string, value: any): Observable<any> {
    return this._http.put(this._backendURL.profile, value
    ).pipe(
      map(_ => this._token.del())
    );
  }

  get(username: string): Observable<any> {
    return this._http.get(this._backendURL.userProfile.replace(':username', username));
  }

  logout(): void {
    this._token.del();
    this.router.navigate(['/login']);
  }

  setUsernameStored(user: string): void{
    this._token.setUsername(user);
  }

  getUsernameStored(): string{
    return this._token.getUser();
  }

  hasStoredToken(): boolean {
    return (
      this._token.get().token &&
      this._token.get().token.length > 0
    );
  }

}
