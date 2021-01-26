import {NgModule} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HomeComponent} from './home/home.component';
import {AddArticleComponent} from './add-article/add-article.component';
import {ArticleComponent} from './article/article.component';
import {UserComponent} from './user/user.component';
import {UserArticleComponent} from './user-article/user-article.component';
import {NotAvailableComponent} from './not-available/not-available.component';

const routes: Routes = [
  { path: '',   redirectTo: '/home', pathMatch: 'full' }, // redirect to `first-component`
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'add-article', component: AddArticleComponent },
  { path: 'article/:id', component: ArticleComponent },
  { path: 'user', component: UserComponent },
  { path: 'my-article/:id', component: UserArticleComponent },
  { path: 'not-available', component: NotAvailableComponent },

  // { path: 'product/:id', component: ProductComponent },
  // { path: 'seller/:id', component: SellerComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
