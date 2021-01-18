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
import {HttpErrorInterceptor} from './shared/interceptors/http-error.interceptor';
import {FormComponent} from './shared/form/form.component';
import {ReactiveFormsModule} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    SearchbarComponent,
    FormComponent
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
        MatGridListModule
    ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
