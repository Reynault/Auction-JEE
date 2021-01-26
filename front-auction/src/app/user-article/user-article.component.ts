import { Component, OnInit } from '@angular/core';
import {Article} from '../shared/interfaces/article';
import {ActivatedRoute, Router} from '@angular/router';
import {ArticleService} from '../shared/services/article.service';
import {AuthService} from '../shared/services/auth.service';
import {ParticipationService} from '../shared/services/participation.service';
import {environment} from '../../environments/environment';
import {Participation} from '../shared/interfaces/participation';
import {User} from '../shared/interfaces/user';

@Component({
  selector: 'app-user-article',
  templateUrl: './user-article.component.html',
  styleUrls: ['./user-article.component.css']
})
export class UserArticleComponent implements OnInit {


  // private property to store people value
  private _id: string;
  private _article: Article;
  private _backendURL: any;
  displayedColumns = ['name', 'price'];
  private readonly _baseUrl: string;

  constructor(private _router: Router,
              private _activatedRoute: ActivatedRoute,
              private _articleService: ArticleService, private _authService: AuthService,
              private _participationService: ParticipationService) {
    this._id = this._activatedRoute.snapshot.url.pop().path;
    this._backendURL = {};
    this._baseUrl = `${environment.backend.protocol}://${environment.backend.host}`;
    if (environment.backend.port) {
      this._baseUrl += `:${environment.backend.port}`;
    }

    this.initArticle();

  }

  get err(): string {
    return this._err;
  }
  private _err: string;

  ngOnInit(): void {
  }

  initArticle(): void {
    this._articleService
      .getUserArticle(this._id).subscribe((article: Article) => {
        this._article = article;
      }
    );
  }

  get participations(): Participation[] {
    if (this.article.auction.participations.length > 0) {
      return this.article.auction.participations;
    }
    else {
      return  [ {
        id: '-1',
        price : '0.0',
        login: 'No participation',
        bider: {
          login: 'No participation',
          name: '',
          lastname: ''
        } as User
      }] as Participation[];
    }
  }


  get article(): Article {
    return this._article;
  }

}
