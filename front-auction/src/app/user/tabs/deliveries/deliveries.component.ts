import { Component, OnInit } from '@angular/core';
import {Article} from '../../../shared/interfaces/article';
import {RouteDescriptor} from '../../../shared/interfaces/route-descriptor';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {DialogComponent} from '../../../shared/form/dialog/dialog.component';
import {Router} from '@angular/router';
import {UserService} from '../../../shared/services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {ArticleService} from '../../../shared/services/article.service';
import {Category} from '../../../shared/interfaces/category';
import {AuctionSend} from '../../../shared/interfaces/auction-send';
import {filter} from 'rxjs/operators';
import {Delivery} from '../../../shared/interfaces/delivery';
import {DeliveryService} from '../../../shared/services/delivery.service';
import {Address} from '../../../shared/interfaces/address';

@Component({
  selector: 'app-deliveries',
  templateUrl: './deliveries.component.html',
  styleUrls: ['./deliveries.component.css']
})
export class DeliveriesComponent implements OnInit {

  private _deliveries: Delivery[];
  displayedColumns = ['id', 'step', 'price', 'address', 'article'];
  private _baseUrl: RouteDescriptor[] = [
    {id: 'delivery', url: '/my-delivery'} as RouteDescriptor,
    {id: 'article', url: '/article'} as RouteDescriptor
  ];

  constructor(private _router: Router, private _authService: AuthService,
              private _deliveryService: DeliveryService) {
    this._deliveries = [];
  }

  ngOnInit(): void {
    this._deliveryService
      .getUserDeliveries().subscribe((deliveries: Delivery[]) => {
      this._deliveries = deliveries;
    });
  }

  get deliveries(): Delivery[] {
    return this._deliveries;
  }

  get baseUrl(): RouteDescriptor[] {
    return this._baseUrl;
  }

  addressTtoString(address: Address): string{
    return '' + address.code
      + ' ' + address.street
      + ', ' + address.city
      + ' - ' + address.country;
  }
}
