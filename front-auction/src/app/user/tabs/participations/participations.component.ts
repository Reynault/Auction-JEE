import {Component, OnInit} from '@angular/core';
import {Article} from '../../../shared/interfaces/article';
import {RouteDescriptor} from '../../../shared/interfaces/route-descriptor';
import {Router} from '@angular/router';
import {AuthService} from '../../../shared/services/auth.service';
import {Category} from '../../../shared/interfaces/category';
import {ParticipationService} from '../../../shared/services/participation.service';
import {Participation} from '../../../shared/interfaces/participation';
import {filter} from 'rxjs/operators';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {DialogDeliveryComponent} from '../../../shared/form/dialog-delivery/dialog-delivery.component';
import {PromotionService} from '../../../shared/services/promotion.service';
import {Promotion} from '../../../shared/interfaces/promotion';
import {DeliveryService} from '../../../shared/services/delivery.service';
import {Address} from '../../../shared/interfaces/address';
import {UserService} from '../../../shared/services/user.service';

@Component({
  selector: 'app-participations',
  templateUrl: './participations.component.html',
  styleUrls: ['./participations.component.css']
})
export class ParticipationsComponent implements OnInit {

  private _articles: Article[];
  private _participations: Participation[];
  private _promotions: Promotion[];
  private _address: Address;
  displayedColumns = ['id', 'name', 'description', 'best', 'last', 'time_limit', 'actions'];
  private _baseUrl: RouteDescriptor[] = [
    {id: 'article', url: '/article'} as RouteDescriptor,
    {id: 'alreadySell', url: '/not-available'} as RouteDescriptor,
    {id: 'article-not-available', url: '/article-ended'} as RouteDescriptor
  ];

  private _dialogStatus: string;
  private _deliveryDialog: MatDialogRef<DialogDeliveryComponent>;

  constructor(private _router: Router, private _authService: AuthService,
              private _participationService: ParticipationService,
              private _userService: UserService,
              private _promotionService: PromotionService,
              private _deliveryService: DeliveryService,
              private _dialog: MatDialog) {
    this._articles = [];
    this._participations = [];
    this._dialogStatus = 'inactive';
  }

  ngOnInit(): void {
    this.refreshParticipations();

    this._promotionService
      .getPromotions().subscribe((promotions: Promotion[]) => {
      this._promotions = promotions;
    });
  }

  get articles(): Article[] {
    return this._articles;
  }

  get participations(): Participation[] {
    return this._participations;
  }

  categories(index: number): Category[]{
    return this._articles[index].categories;
  }

  get baseUrl(): RouteDescriptor[] {
    return this._baseUrl;
  }

  getDate(): Date{
    return new Date();
  }

  toDate(date: string): Date{
    return new Date(date);
  }

  /**
   * Function to display modal
   */
  showDialog(_id: string): void {
    // set dialog status
    this._dialogStatus = 'active';
    // open modal
    this._deliveryDialog = this._dialog.open(DialogDeliveryComponent, {
      width: '500px',
      disableClose: true,
      data: {promo : this._promotions, address: this._address, id: _id}
    });

    this._deliveryDialog.afterClosed()
      .pipe(
        filter(_ => !!_))
      .subscribe(
        (_) => {
          this.createDelivery(_id, _.promo.value, _.address);
          },
        _ => this._dialogStatus = 'inactive',
        () => this._dialogStatus = 'inactive'
      );
  }

  createDelivery(id: string, promo: [], address: Address): void{
    this._deliveryService.createDelivery(id, promo, address).subscribe(_ => {this.refreshParticipations(); });
  }

  getUserLastParticipation(article: Article): Participation{
    return (article.auction.participations.filter(p => p.login === this._authService.getUsernameStored()))
      .reduce((p, c) => (p.price > c.price) ? p : c);

  }

  refreshParticipations(): void{
    this._participationService
      .getParticipations().subscribe((articles: Article[]) => {
      this._articles = articles;
      this._articles.forEach((article: Article) => {
        this._participations.push(this.getUserLastParticipation(article));
      });
      this._userService.getUserAddress().subscribe((address: Address) => {
        this._address = address;
        delete this._address.id;
      });
    });
  }

  isEndedOrNot(article: Article): number{
    console.log("TEST");
    if (this.toDate(article.auction.timeLimit) > this.getDate()){
      return 0;
    }else{
      return 0;
    }
  }


}
