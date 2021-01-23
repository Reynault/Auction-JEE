import { Injectable } from '@angular/core';
import {User} from '../interfaces/user';
import {Observable} from 'rxjs';
import {defaultIfEmpty, filter} from 'rxjs/operators';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {TokenService} from './token-service';
import {Router} from '@angular/router';
import {Article} from '../interfaces/article';
import {Category} from '../interfaces/category';
import {Auction} from '../interfaces/auction';
import {ArticleSend} from '../interfaces/article-send';
import {AuctionSend} from '../interfaces/auction-send';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private readonly _backendURL: any;
  private readonly _defaultArticle: Article;
  private readonly _defaultArticleSend: ArticleSend;

  constructor(private _http: HttpClient,
              private _token: TokenService,
              private router: Router) {
    this._defaultArticle = {
      id: '-1',
      name: 'No Article',
      description: 'No Descrition',
      categories: [{
        id: '-1',
        name: 'No Category'
      } as Category],
      auction: {
        id: '-1',
        firstPrice: '0.0',
        timeLimit: '05/05/2020',
        best: {
          id: '-1',
          price: '0.0',
          bider: {} as User
        },
        participations: [{
          id: '-1',
          price: '0.0',
          bider: {
            login: 'No User',
            name: 'No name',
            lastname: 'No Last'
          } as User
        }]
      } as Auction,
      hasBeenSold: false
    };
    this._defaultArticleSend = {
      name: 'Default Article',
      description: 'Description',
      categories: [
        'null'
      ]
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
   * Récupérer les articles qui sont en vente sur le marché
   * dont la date limite n'est pas encore passée
   */
  getArticles(): Observable<Article[]> {
    return this._http.get<Article[]>(this._backendURL.articles);
  }

  /**
   * Récupérer les articles d'un utilisateur
   */
  getUserArticles(): Observable<Article[]> {
    return this._http.get<Article[]>(this._backendURL.allUserArticles);
  }

  /**
   * Récupérer l'article d'un utilisateur
   */
  getUserArticle(id: string): Observable<Article> {
    return this._http.get<Article>(this._backendURL.articleUser.replace(':id', id));
  }

  getArticle(id: string): Observable<Article> {
    return this._http.get<Article>(this._backendURL.article.replace(':id', id));
  }

  get defaultArticle(): Article {
    return this._defaultArticle;
  }

  get defaultArticleSend(): ArticleSend {
    return this._defaultArticleSend;
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

  create(article: ArticleSend): Observable<any>{
    return this._http.post(this._backendURL.addArticle, article);
  }

  // modify(id: string, recipe: any): Observable<any> {
  //   return this._http.put(this._backendURL.oneArticle.replace(':id', id), recipe);
  // }

  sell(id: string, articleAuction: AuctionSend): Observable<any> {
    console.log('id' + id);
    console.log(articleAuction);
    return this._http.post(this._backendURL.addAuction.replace(':id', id), articleAuction);
  }

  /**
   * Retire un article de la vente
   * @param id, id de l'article
   */
  remove(id: string): Observable<any> {
    return this._http.post(this._backendURL.deleteAuction.replace(':id', id), {});
  }

  /**
   * Supprime un article définitivement
   * @param id, id de l'article
   */
  delete(id: string): Observable<any> {
    return this._http.delete(this._backendURL.deleteArticle.replace(':id', id));
  }
}
