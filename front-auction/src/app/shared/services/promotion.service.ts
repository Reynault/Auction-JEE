import { Injectable } from '@angular/core';
import {User} from '../interfaces/user';
import {Observable} from 'rxjs';
import {defaultIfEmpty, filter} from 'rxjs/operators';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {TokenService} from './token-service';
import {Router} from '@angular/router';
import {Article} from '../interfaces/article';
import {Promotion} from '../interfaces/promotion';
import {Parameter} from '../interfaces/parameter';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {

  private readonly _backendURL: any;
  private readonly _defaultPromotion: Promotion;

  constructor(private _http: HttpClient,
              private _token: TokenService,
              private router: Router) {
    this._defaultPromotion = {
      id: 'null',
      description: 'Description',
      parameters: [{} as Parameter],
      daily: true,
      type: ''
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
   * Récupérer la liste des promotions (utilisateur connecté seulement)
   */
  getPromotions(): Observable<Promotion[]> {
    return this._http.get<Promotion[]>(this._backendURL.promotions);
  }

}
