import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../shared/services/auth.service';
import {UserService} from '../shared/services/user.service';
import {ArticleService} from '../shared/services/article.service';
import {Article} from '../shared/interfaces/article';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  private _articles: Article[];

  constructor(private _router: Router, private _userService: UserService, private _authService: AuthService,
              private _articleService: ArticleService) {
    this._articles = [];
  }

  ngOnInit(): void {
    this._articleService
      .getArticles().subscribe((articles: Article[]) => {
      this._articles = articles;
      for (let i = 0; i < 20; i++){
        this.articles.push(articles[i]);
      }
      console.log(articles);
    });
  }

  get articles(): Article[] {
    return this._articles;
  }

  isLogin(): boolean{
    return this._authService.hasStoredToken();
  }


}
