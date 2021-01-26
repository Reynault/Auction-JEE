import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {TokenService} from './token-service';
import {Promotion} from '../interfaces/promotion';
import {Parameter} from '../interfaces/parameter';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {

  private readonly _backendURL: any;
  private readonly _defaultPromotion: Promotion;

  constructor(private _http: HttpClient,
              private _token: TokenService) {
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

  calculatePromotions(id: string, promotions: any): Observable<any>{
    let params = new HttpParams();
    promotions.forEach((x) => {
      params = params.append('o', x[0].id);
    });
    return this._http.get<Promotion[]>(this._backendURL.calculatePromotions.replace(':id', id), {params: params});
  }

}
