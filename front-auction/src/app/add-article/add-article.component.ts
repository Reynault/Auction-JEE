import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {Article} from '../shared/interfaces/article';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../shared/services/auth.service';
import {TokenService} from '../shared/services/token-service';
import {ArticleService} from '../shared/services/article.service';
import {merge} from 'rxjs';
import {filter, mergeMap} from 'rxjs/operators';
import {errorMessages} from '../shared/constants/error.messages';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
export class AddArticleComponent implements OnInit {

  /**
   * Component constructor
   */
  constructor(private _articleService: ArticleService,
              private _route: ActivatedRoute,
              private _router: Router,
              private _auth: AuthService,
              private _token: TokenService) {
    this._article = {} as Article;

    this._isUserArticle = false;
    this._createArticle = true;

    this._form = AddArticleComponent._buildBuild();
    this._formCategory = this._form.get('categories') as FormArray;
  }

  @Input()
  set createArticle(value: boolean) {
    this._createArticle = value;
  }

  get createArticle(): boolean {
    return this._createArticle;
  }

  set article(value: Article) {
    this._article = value;
  }

  get article(): Article {
    return this._article;
  }
  get formCategory(): FormArray {
    return this._formCategory;
  }

  /**
   * Returns flag to know if we are on a profile or on HP
   */
  get isUserArticle(): boolean {
    return this._isUserArticle;
  }

  get form(): FormGroup {
    return this._form;
  }

  get err(): string {
    return this._err;
  }
  private _isUserArticle: boolean;

  private _createArticle: boolean;
  private readonly _form: FormGroup;
  private _formCategory: FormArray;

  private _article: Article;
  private _err: string;

  private static _buildBuild(): FormGroup {
    return new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      categories: new FormArray([])
    });
  }

  addCategory(n: string): void {
    const group = new FormGroup({
      name: new FormControl(n, Validators.required)
    });

    this.formCategory.push(group);
  }

  removeCategory(i: number): void {
    this.formCategory.removeAt(i);
  }

  /**
   * OnInit implementation
   */
  ngOnInit(): void {
    merge(
      this._route.params.pipe(
        filter(params => !!params.id),
        mergeMap(params => this._articleService.fetchOne(params.id)),
      )
    ).subscribe(
      (article: any) => {
        this._article = article;
        if (this._token.hasToken()) {
          this._articleService.userHasArticle(article.id).subscribe(
            val => {
              this._isUserArticle = val;
            },
            error => {
              this.handleError(error);
            }
          );
        }
        this.mapPropertiesWithForm();
      },
      error => {
        this._article = this._articleService.defaultArticle;
      }
    );
  }

  delete(): void {
    this._articleService.delete(this.article.id).subscribe(
      () => this._router.navigate(['/my-articles']),
      error => this.handleError(error)
    );
  }

  // update(): void {
  //   if (this.form.valid) {
  //     const v = this.form.value;
  //     Object.assign(v, {username: this._token.getUser()});
  //     this._articleService.modify(this.article.id, this.form.value).subscribe(
  //       () => this._router.navigate(['/my-articles']),
  //       error => this.handleError(error)
  //     );
  //   }
  // }

  insert(): void {
    if (this.form.valid) {
      Object.assign(this.article, this.form.value);
      Object.assign(this.article, {username: this._token.getUser()});
      this._articleService.create(this._token.get().token, this.article).subscribe(
        () => this._router.navigate(['/my-articles']),
        error => this.handleError(error)
      );
    }
  }

  mapPropertiesWithForm(): void {
    this.form.patchValue(this.article);
    this.article.categories.forEach(category =>
      this.addCategory(
        category.name
      )
    );
  }

  getRouterLink(): string {
    if (!this._isUserArticle) {
      return '/articles';
    } else {
      return '/my-articles';
    }
  }

  handleError(error): void{
    switch (error.status){
      case 401:
        this._err = errorMessages.unauthorizedError;
        break;
      case 404:
        this._err = errorMessages.notFound;
        break;
      default:
        this._err = errorMessages.serverError;
        break;
    }
  }

}
