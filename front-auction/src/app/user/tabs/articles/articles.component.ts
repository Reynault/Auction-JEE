import { Component, OnInit } from '@angular/core';
import {PeriodicElement} from '../../user.component';
import {Article} from '../../../shared/interfaces/article';
import {Router} from '@angular/router';
import {UserService} from '../../../shared/services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ArticleService} from '../../../shared/services/article.service';
import {Category} from '../../../shared/interfaces/category';
import {RouteDescriptor} from '../../../shared/interfaces/route-descriptor';
import {filter, map, mergeMap} from 'rxjs/operators';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Auction} from '../../../shared/interfaces/auction';
import {AuctionSend} from '../../../shared/interfaces/auction-send';
import {AuctionFormComponent} from '../../../shared/form/auction-form/auction-form.component';
import {DialogComponent} from '../../../shared/form/dialog/dialog.component';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  private _articles: Article[];
  displayedColumns = ['id', 'name', 'description', 'categories', 'actions'];
  private _baseUrl: RouteDescriptor[] = [
    {id: 'article', url: '/my-article'} as RouteDescriptor
  ];
  private _dialogStatus: string;
  private _auctionDialog: MatDialogRef<DialogComponent>;

  constructor(private _router: Router, private _userService: UserService, private _authService: AuthService,
              private _articleService: ArticleService, private _dialog: MatDialog) {
    this._articles = [];
    this._dialogStatus = 'inactive';

  }

  ngOnInit(): void {
    this._articleService
      .getUserArticles().subscribe((articles: Article[]) => {
      this._articles = articles;
    });
  }

  get articles(): Article[] {
    return this._articles;
  }

  categories(index: number): Category[]{
    return this._articles[index].categories;
  }

  get baseUrl(): RouteDescriptor[] {
    return this._baseUrl;
  }

  delete(id: string): void{
    this._articleService.delete(id).subscribe(_ => this.refreshArticles());
  }

  sell(id: string, auction: AuctionSend): void{
    delete auction.id;
    this._articleService.sell(id, auction).subscribe(_ => this.refreshArticles());
  }

  remove(id: string): void{
    this._articleService.remove(id).subscribe(_ => this.refreshArticles());
  }

  getDate(): Date{
    return new Date();
  }

  /**
   * Returns private property _dialogStatus
   */
  get dialogStatus(): string {
    return this._dialogStatus;
  }

  /**
   * Function to display modal
   */
  showDialog(_id: string): void {
    // set dialog status
    this._dialogStatus = 'active';
    // open modal
    this._auctionDialog = this._dialog.open(DialogComponent, {
      width: '500px',
      disableClose: true,
      data: {id: _id}
    });

    this._auctionDialog.afterClosed()
      .pipe(
        filter(_ => !!_))
      .subscribe(
        (_) => {this.sell(_id, _); },
        _ => this._dialogStatus = 'inactive',
        () => this._dialogStatus = 'inactive'
      );
  }

  toDate(date: string): Date{
    return new Date(date);
  }

  refreshArticles(): void{
    this._articleService.getUserArticles().subscribe((articles: Article[]) => {
      this._articles = articles;
    });
  }

}
