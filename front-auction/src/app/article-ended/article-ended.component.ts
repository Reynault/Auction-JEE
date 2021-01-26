import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ArticleService} from '../shared/services/article.service';
import {AuthService} from '../shared/services/auth.service';
import {environment} from '../../environments/environment';
import {Article} from '../shared/interfaces/article';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {errorMessages} from '../shared/constants/error.messages';
import {ParticipationService} from '../shared/services/participation.service';

@Component({
  selector: 'app-article',
  templateUrl: './article-ended.component.html',
  styleUrls: ['./article-ended.component.css']
})
export class ArticleEndedComponent implements OnInit {

  // private property to store people value
  private _id: string;
  private _article: Article;
  private _backendURL: any;
  private readonly _baseUrl: string;

  constructor(private _router: Router,
              private _activatedRoute: ActivatedRoute,
              private _articleService: ArticleService,
              private _authService: AuthService,
              private _participationService: ParticipationService) {
    this._id = this._activatedRoute.snapshot.url.pop().path;
    console.log(this._id);
    this._backendURL = {};
    this._baseUrl = `${environment.backend.protocol}://${environment.backend.host}`;
    if (environment.backend.port) {
      this._baseUrl += `:${environment.backend.port}`;
    }
  }

  get form(): FormGroup {
    return this._form;
  }

  get err(): string {
    return this._err;
  }
  private _form: FormGroup;
  private _err: string;

  ngOnInit(): void {
    this._participationService.getParticipation(this._id).subscribe((article: Article) => {
      this._article = article;
    });
  }

  get article(): Article {
    return this._article;
  }


}
