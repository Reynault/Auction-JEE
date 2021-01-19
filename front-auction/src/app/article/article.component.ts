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
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {

  // private property to store people value
  private _id: string;
  private _article: Article;
  private _backendURL: any;
  private readonly _baseUrl: string;

  constructor(private _router: Router, private _activatedRoute: ActivatedRoute, private _articleService: ArticleService,
              private _authService: AuthService, private _participationService: ParticipationService) {
    this._id = this._activatedRoute.snapshot.url.pop().path;
    this._backendURL = {};
    this._baseUrl = `${environment.backend.protocol}://${environment.backend.host}`;
    if (environment.backend.port) {
      this._baseUrl += `:${environment.backend.port}`;
    }

    this.initArticle();

  }

  get form(): FormGroup {
    return this._form;
  }

  get err(): string {
    return this._err;
  }
  private _form: FormGroup;
  private _err: string;

  private static _buildForm(bestBide: string): FormGroup {
    return new FormGroup({
      price: new FormControl('', Validators.compose([
        Validators.required,
        (control: AbstractControl) => Validators.min(parseFloat(bestBide))(control),
        Validators.maxLength(50)
      ]))
    });
  }

  ngOnInit(): void {
  }

  initArticle(): void {
    this._articleService
      .getArticle(this._id).subscribe((article: Article) => {
        this._article = article;
        this._form = ArticleComponent._buildForm(this._article.auction.best.price);
      }
    );
  }

  submit(): void {
    if (this.form.valid) {
      console.log(this.form.value);
      this._participationService.participate(this._article.id, this.form.value).subscribe(
        () => {
          this._router.navigate(['/home']);
        },
        (error) => {
          console.log(error);
          switch (error.status) {
            case 401:
              this._err = errorMessages.wrongData;
              break;
            default:
              this._err = errorMessages.serverError;
              break;
          }
        }
      );
    }
  }

  get article(): Article {
    return this._article;
  }

  isLogin(): boolean{
    return this._authService.hasStoredToken();
  }

}
