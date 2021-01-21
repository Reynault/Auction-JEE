import { Component, OnInit } from '@angular/core';
import {Article} from '../../../shared/interfaces/article';
import {RouteDescriptor} from '../../../shared/interfaces/route-descriptor';
import {Router} from '@angular/router';
import {UserService} from '../../../shared/services/user.service';
import {AuthService} from '../../../shared/services/auth.service';
import {Category} from '../../../shared/interfaces/category';
import {ParticipationService} from '../../../shared/services/participation.service';
import {Participation} from '../../../shared/interfaces/participation';

@Component({
  selector: 'app-participations',
  templateUrl: './participations.component.html',
  styleUrls: ['./participations.component.css']
})
export class ParticipationsComponent implements OnInit {

  private _articles: Article[];
  private _participations: Participation[];
  displayedColumns = ['id', 'name', 'description', 'best', 'last', 'time_limit'];
  private _baseUrl: RouteDescriptor[] = [
    {id: 'article', url: '/article'} as RouteDescriptor,
    {id: 'alreadySell', url: '/not-available'} as RouteDescriptor
  ];

  constructor(private _router: Router, private _userService: UserService, private _authService: AuthService,
              private _participationService: ParticipationService) {
    this._articles = [];
    this._participations = [];
  }

  ngOnInit(): void {
    this._participationService
      .getParticipations().subscribe((articles: Article[]) => {
      this._articles = articles;
      this._articles.forEach((article: Article) => {
        this._participations.push(this.getUserLastParticipation(article));
      });
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

  getUserLastParticipation(article: Article): Participation{
    // return
    console.log(article.auction.participations);
    const maxPeak = (article.auction.participations.filter(p => p['login'] === this._authService.getUsernameStored()))
      .reduce((p, c) => (p.price > c.price) ? p : c);
    console.log(maxPeak);
    return maxPeak;

  }

}
