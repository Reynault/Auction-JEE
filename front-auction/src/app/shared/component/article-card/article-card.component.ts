import {Component, Input, OnInit} from '@angular/core';
import {Article} from '../../interfaces/article';
import {Router} from '@angular/router';
import {ArticleService} from '../../services/article.service';
import {RouteDescriptor} from '../../interfaces/route-descriptor';

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.css']
})
export class ArticleCardComponent implements OnInit {

  @Input()
  private _article: Article;

  private _isDragging: boolean;

  // private property to store people value
  private _baseUrl: RouteDescriptor[] = [
    {id: 'article', url: '/article'} as RouteDescriptor
  ];

  constructor(private _router: Router, private _articlesService: ArticleService) {

  }

  ngOnInit(): void {
  }


  get article(): Article {
    return this._article;
  }


  set article(value: Article) {
    this._article = value;
  }

  get baseUrl(): RouteDescriptor[] {
    return this._baseUrl;
  }
}
