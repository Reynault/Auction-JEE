import { Injectable } from '@angular/core';
import {User} from '../interfaces/user';
import {Observable} from 'rxjs';
import {Token} from '../interfaces/token';
import {defaultIfEmpty, filter, map} from 'rxjs/operators';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {TokenService} from './token-service';
import {Router} from '@angular/router';
import {Article} from '../interfaces/article';
import {Category} from '../interfaces/category';
import {Auction} from '../interfaces/auction';
import {Participation} from '../interfaces/participation';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private readonly _backendURL: any;
  private readonly _defaultArticle: Article;

  constructor(private _http: HttpClient,
              private _token: TokenService,
              private router: Router) {
    this._defaultArticle = {
      id: 'null',
      name: 'Default Article',
      description: 'Description',
      categories: [{
        id: 'null',
        name: 'Category Name'
      } as Category],
      auction: {
        id: 'null',
        firstPrice: '0.0',
        timeLimit: '05/05/2020',
        best: '0.0',
        participations: [{
          id: 'null',
          firstPrice: '0.0'
        }]
      } as Auction
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

  getArticles(): Observable<any> {
    return this._http.get<Token>(this._backendURL.articles);
  }

  get defaultArticle(): Article {
    return this._defaultArticle;
  }

  /**
   * Retourne la liste des recettes
   */
  fetch(): Observable<Article[]> {
    return this._http.get<Article[]>(this._backendURL.allArticles)
      .pipe(
        filter(_ => !!_),
        defaultIfEmpty([])
      );
  }

  /**
   * Retourne la recette correspondant à l'id courant
   */
  fetchOne(id: string): Observable<Article> {
    return this._http.get<Article>(this._backendURL.oneArticle.replace(':id', id));
  }

  userHasArticle(id: string): Observable<boolean>{
    return this._http.get<boolean>(this._backendURL.hasArticle, {params: {id}});
  }

  create(token: string, article: Article): Observable<any>{
    console.log(token);
    console.log(article);
    return this._http.post(this._backendURL.addArticle, article);
  }

  // modify(id: string, recipe: any): Observable<any> {
  //   return this._http.put(this._backendURL.oneArticle.replace(':id', id), recipe);
  // }

  delete(id: string): Observable<any> {
    return this._http.delete(this._backendURL.deleteArticle.replace(':id', id));
  }
}
