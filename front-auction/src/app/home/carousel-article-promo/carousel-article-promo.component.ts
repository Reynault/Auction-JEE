import { Component, OnInit } from '@angular/core';
import {Article} from '../../shared/interfaces/article';
import {Router} from '@angular/router';
import {environment} from '../../../environments/environment';
import {ArticleService} from '../../shared/services/article.service';
import {OwlOptions} from 'ngx-owl-carousel-o';

@Component({
  selector: 'app-carousel-article-promo',
  templateUrl: './carousel-article-promo.component.html',
  styleUrls: ['./carousel-article-promo.component.css']
})
export class CarouselArticlePromoComponent implements OnInit {

  // private property to store people value
  private _articles: Article[];
  private _baseUrl: string;

  constructor(private _router: Router, private _articlesService: ArticleService) {
    this._articles = [];
    this._baseUrl = `${environment.backend.protocol}://${environment.backend.host}`;
    if (environment.backend.port) {
      this._baseUrl += `:${environment.backend.port}`;
    }
  }

  customOptions: OwlOptions = {
    mouseDrag: true,
    lazyLoad: true,
    touchDrag: true,
    pullDrag: true,
    dots: false,
    navSpeed: 600,
    margin: 10,
    autoWidth: true,
    autoHeight: true,
    responsive: {
      0: {
        items: 1
      },
      600: {
        items: 2
      },
      900: {
        items: 8
      }
    },
    nav: false
  };

  /**
   * Returns private property _categories
   */
  get articles(): Article[] {
    return this._articles;
  }

  /**
   * OnInit implementation
   */
  ngOnInit(): void {
    this._articlesService
      .getArticles().subscribe((articles: Article[]) => {
      this._articles = articles;
    });
  }

  /**
   * Function to navigate to current category
   */
  navigate(id: string): void {
    this._router.navigate(['/category', id]);
  }

}
