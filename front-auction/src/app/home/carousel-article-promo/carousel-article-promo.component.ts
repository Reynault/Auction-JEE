import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {environment} from '../../../environments/environment';
import {OwlOptions} from 'ngx-owl-carousel-o';
import {Promotion} from '../../shared/interfaces/promotion';
import {PromotionService} from '../../shared/services/promotion.service';

@Component({
  selector: 'app-carousel-article-promo',
  templateUrl: './carousel-article-promo.component.html',
  styleUrls: ['./carousel-article-promo.component.css']
})
export class CarouselArticlePromoComponent implements OnInit {

  // private property to store people value
  private _promotions: Promotion[];
  private readonly _baseUrl: string;

  constructor(private _router: Router, private _promotionService: PromotionService) {
    this._promotions = [];
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
  get promotions(): Promotion[] {
    return this._promotions;
  }

  /**
   * OnInit implementation
   */
  ngOnInit(): void {
    this._promotionService
      .getPromotions().subscribe((promotions: Promotion[]) => {
      this._promotions = promotions;
    });
  }

}
