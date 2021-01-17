import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { environment } from '../../../environments/environment';
import {Observable, throwError} from 'rxjs';
import { User } from '../interfaces/user';
import {catchError, defaultIfEmpty, filter, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  // private property to store all backend URLs
  private readonly _backendURL: any;
  // private property to store default user
  private readonly _defaultUser: User;

  constructor(private _http: HttpClient) {
    // this._defaultUser = {
    //   id: '5fbc3bef49ba5b4ca3e4b42d',
    //   thumbnail: 'https://randomuser.me/api/portraits/lego/6.jpg',
    //   username: 'Username',
    //   verified: true,
    //   subscriptions: [],
    //   likes: [],
    // };
    this._backendURL = {};

    // build backend base url
    let baseUrl = `${environment.backend.protocol}://${environment.backend.host}`;
    if (environment.backend.port) {
      baseUrl += `:${environment.backend.port}`;
    }

    // build all backend urls
    Object.keys(environment.backend.endpoints).forEach(k => this._backendURL[ k ] = `${baseUrl}${environment.backend.endpoints[ k ]}`);
  }

  /**
   * Returns private property _defaultUser
   */
  get defaultUser(): User {
    return this._defaultUser;
  }

  test404Resources(query: string): Observable<any> {
    return this._http
      .get(query)
      .pipe(
        map(() => {}),
        catchError((err: HttpErrorResponse) => {
          return throwError(err);
        })
      );
  }


  /**
   * Function to return list of user
   */
  fetch(): Observable<User[]> {
    return this._http.get<User[]>(this._backendURL.allUsers)
      .pipe(
        filter(_ => !!_),
        defaultIfEmpty([])
      );
  }

  /**
   * Function to return one random user from users list
   */
  fetchRandom(): Observable<User> {
    return this._http.get<User>(this._backendURL.randomUsers)
      .pipe(
        filter(_ => !!_),
        defaultIfEmpty(this._defaultUser)
      );
  }

  /**
   * Function to return one user for current id
   */
  fetchOne(id: string): Observable<User> {
    return this._http.get<User>(this._backendURL.oneUsers.replace(':id', id));
  }

  /**
   * Function to return one user for current id
   */
  fetchOneByUsername(username: string): Observable<User> {
    return this._http.get<User>(this._backendURL.oneUserByUsername.replace(':username', username));
  }

  /**
   * Function to create a new user
   */
  login(user: User): Observable<any> {
    return this._http.post<User>(this._backendURL.login, user, this._options());
  }

  /**
   * Function to create a new user
   */
  create(user: User): Observable<any> {
    return this._http.post<User>(this._backendURL.allUsers, user, this._options());
  }

  /**
   * Function to update one user
   */
  update(id: string, user: User): Observable<any> {
    return this._http.put<User>(this._backendURL.oneUsers.replace(':id', id), user, this._options());
  }

  /**
   * Function to delete one user for current id
   */
  delete(id: string): Observable<string> {
    return this._http.delete(this._backendURL.oneUsers.replace(':id', id))
      .pipe(
        map(_ => id)
      );
  }

  /**
   * Function to return request options
   */
  private _options(headerList: object = {}): any {
    return { headers: new HttpHeaders(Object.assign({ 'Content-Type': 'application/json' }, headerList)) };
  }
}
