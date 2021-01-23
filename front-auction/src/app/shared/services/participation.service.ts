import { Injectable } from '@angular/core';
import {User} from '../interfaces/user';
import {Observable} from 'rxjs';
import {defaultIfEmpty, filter} from 'rxjs/operators';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {TokenService} from './token-service';
import {Router} from '@angular/router';
import {Participation} from '../interfaces/participation';
import {ParticipationSend} from '../interfaces/participation-send';
import {Article} from '../interfaces/article';

@Injectable({
  providedIn: 'root'
})
export class ParticipationService {

  private readonly _backendURL: any;
  private readonly _defaultParticipation: Participation;

  constructor(private _http: HttpClient,
              private _token: TokenService,
              private router: Router) {
    this._defaultParticipation = {
      id: '-1',
      price : '0.0',
      bider: {
        login: 'No  Nogin',
        name: 'No name',
        lastname: 'No lastname'
      } as User
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

  getParticipations(): Observable<Article[]> {
    return this._http.get<Article[]>(this._backendURL.participations);
  }

  getParticipation(id: string): Observable<Article> {
    return this._http.get<Article>(this._backendURL.participation.replace(':id', id));
  }


  get defaultParticipation(): Participation {
    return this._defaultParticipation;
  }


  participate(id: string, participation: ParticipationSend): Observable<any>{
    return this._http.post(this._backendURL.participate.replace(':id', id), participation);
  }

  delete(id: string): Observable<any> {
    return this._http.delete(this._backendURL.deleteParticipation.replace(':id', id));
  }
}
