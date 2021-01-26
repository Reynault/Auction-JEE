import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {LoginComponent} from './login/login.component';
import {MatButtonModule} from '@angular/material/button';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { SearchbarComponent } from './shared/component/searchbar/searchbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatInputModule} from '@angular/material/input';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import { CarouselArticlePromoComponent } from './home/carousel-article-promo/carousel-article-promo.component';
import {CarouselModule} from 'ngx-owl-carousel-o';
import { AddArticleComponent } from './add-article/add-article.component';
import {AuthorizationInterceptor} from './shared/interceptors/authorization.interceptor';
import { ArticleCardComponent } from './shared/component/article-card/article-card.component';
import { ArticlePromotionCardComponent } from './shared/component/article-promotion-card/article-promotion-card.component';
import { ArticleComponent } from './article/article.component';
import { UserComponent } from './user/user.component';
import {MatTableModule} from '@angular/material/table';
import { UserTabsComponent } from './user/tabs/user-tabs.component';
import { ArticlesComponent } from './user/tabs/articles/articles.component';
import { AuctionsComponent } from './user/tabs/auctions/auctions.component';
import {MatTabsModule} from '@angular/material/tabs';
import { UserArticleComponent } from './user-article/user-article.component';
import { ParticipationsComponent } from './user/tabs/participations/participations.component';
import { DeliveriesComponent } from './user/tabs/deliveries/deliveries.component';
import { AuctionFormComponent } from './shared/form/auction-form/auction-form.component';
import {MatDialogModule} from '@angular/material/dialog';
import { DialogComponent } from './shared/form/dialog/dialog.component';
import { NotAvailableComponent } from './not-available/not-available.component';
import {DialogDeliveryComponent} from './shared/form/dialog-delivery/dialog-delivery.component';
import {DeliveryFormComponent} from './shared/form/delivery-form/delivery-form.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    SearchbarComponent,
    CarouselArticlePromoComponent,
    AddArticleComponent,
    ArticleCardComponent,
    ArticlePromotionCardComponent,
    ArticleComponent,
    UserComponent,
    UserTabsComponent,
    ArticlesComponent,
    AuctionsComponent,
    UserArticleComponent,
    ParticipationsComponent,
    DeliveriesComponent,
    AuctionFormComponent,
    DeliveryFormComponent,
    DialogComponent,
    DialogDeliveryComponent,
    NotAvailableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatAutocompleteModule,
    MatIconModule,
    MatMenuModule,
    MatInputModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatCardModule,
    MatGridListModule,
    CarouselModule,
    MatTableModule,
    MatTabsModule,
    MatDialogModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthorizationInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
