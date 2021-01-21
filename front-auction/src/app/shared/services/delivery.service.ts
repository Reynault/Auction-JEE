import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {TokenService} from './token-service';
import {Router} from '@angular/router';
import {Delivery} from '../interfaces/delivery';
import {Address} from '../interfaces/address';
import {ArticleToDeliver} from '../interfaces/article-to-deliver';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  private readonly _backendURL: any;
  private readonly _defaultDelivery: Delivery;

  constructor(private _http: HttpClient,
              private _token: TokenService,
              private router: Router) {
    this._defaultDelivery = {
      id: '-1',
      step: 'No Delivery',
      price: 'No Descrition',
      address: {
        id: '-1',
        country: 'Not precised',
        city: 'Not precised',
        street: 'Not precised',
        code: 'Not precised'
      } as Address,
      article: {
        id: '-1',
        name: '0.0',
        description: 'No description',
      } as ArticleToDeliver
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
   * Récupérer toutes les commandes
   */
  getDeliveries(): Observable<Delivery[]> {
    return this._http.get<Delivery[]>(this._backendURL.articles);
  }

  /**
   * Récupérer les commandes d'un utilisateur
   */
  getUserDeliveries(): Observable<Delivery[]> {
    return this._http.get<Delivery[]>(this._backendURL.userDeliveries);
  }

  /**
   * Récupérer la commande d'un utilisateur
   */
  getUserDelivery(id: string): Observable<Delivery> {
    return this._http.get<Delivery>(this._backendURL.userDelivery.replace(':id', id));
  }

  /**
   * Récupérer une commande
   * @param id, id dela commande
   */
  getDelivery(id: string): Observable<Delivery> {
    return this._http.get<Delivery>(this._backendURL.article.replace(':id', id));
  }

  get defaultDelivery(): Delivery {
    return this._defaultDelivery;
  }

}
