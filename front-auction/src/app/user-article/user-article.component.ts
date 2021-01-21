import { Component, OnInit } from '@angular/core';
import {Article} from '../shared/interfaces/article';
import {ActivatedRoute, Router} from '@angular/router';
import {ArticleService} from '../shared/services/article.service';
import {AuthService} from '../shared/services/auth.service';
import {ParticipationService} from '../shared/services/participation.service';
import {environment} from '../../environments/environment';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {errorMessages} from '../shared/constants/error.messages';

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
        console.log(article);
        this._article = article;
      }
    );
  }


  get article(): Article {
    return this._article;
  }

  isLogin(): boolean{
    return this._authService.hasStoredToken();
  }

}
