import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../shared/services/auth.service';
import {UserService} from '../shared/services/user.service';
import {ArticleService} from '../shared/services/article.service';
import {Article} from '../shared/interfaces/article';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  private _articles: Article[];


  private _search: EventEmitter<any>;

  private _values: FormGroup;

  constructor(private _router: Router, private _userService: UserService,
              private _authService: AuthService, private _articleService: ArticleService,
              private _route: ActivatedRoute) {
    this._articles = [];
    this._values = this.buildForm();
    this._search = new EventEmitter<any>();
  }

  ngOnInit(): void {
    this._route.queryParams.subscribe(params => {
      // fetching params
      this._values.patchValue(params);
      // request
      this.fetchData(params);
    });
  }

  buildForm(): FormGroup{
    return new FormGroup({
      c: new FormControl('', Validators.compose([Validators.maxLength(255)])),
      n: new FormControl('', Validators.compose([Validators.maxLength(255)])),
    });
  }

  launchSearch(): void {
    // this function will reload the page and refresh all the query parameters
    // based on searched values

    // first we verify if every searched values are valids
    if (this._values.valid) {
      let values = this._values.value;
      let params = {};

      // then we will filter them to search only the non-empty values
      Object.keys(values).forEach(_ => {
        if (values[_]){ // check if value is not empty, undefined, [], or {}
          // console.log(values[_]);
          let split_array = values[_].split(',');
          split_array.forEach(__ => {
            Object.assign(params, {[_] : __});
          });
          // Object.assign(params, {[_] : values[_]});
        }
      });

      // then, we reload the page with the new query parameters
      if (params){
        this._router.navigate(['/home'], {
          queryParams: params
        });
      }else{
        this._router.navigate(['/home']);
      }
    }
  }

  fetchData(params): void{
    this._articleService
      .getArticles(params).subscribe((articles: Article[]) => {
        console.log(articles);
        this._articles = articles;
    });
  }

  set values(value: FormGroup) {
    this._values = value;
  }

  get values(): FormGroup {
    return this._values;
  }

  @Output('search')
  get search(): EventEmitter<any> {
    return this._search;
  }

  set search(value: EventEmitter<any>) {
    this._search = value;
  }

  get articles(): Article[] {
    return this._articles;
  }

  isLogin(): boolean{
    return this._authService.hasStoredToken();
  }


}
