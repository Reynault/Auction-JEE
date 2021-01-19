import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticlePromotionCardComponent } from './article-promotion-card.component';

describe('ArticlePromotionCardComponent', () => {
  let component: ArticlePromotionCardComponent;
  let fixture: ComponentFixture<ArticlePromotionCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArticlePromotionCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticlePromotionCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
