import {Component, OnInit, Output} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {EventEmitter} from 'events';

@Component({
  selector: 'app-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.css']
})
export class SearchbarComponent implements OnInit {

  @Output()
  openDr: EventEmitter<boolean> = new EventEmitter();

  constructor(private _authService: AuthService) {  }

  ngOnInit(): void {
  }

  clickMenu(): void {
    this.openDr.emit(true);
  }

  isLogedIn(): boolean {
    return this._authService.hasStoredToken();
  }

  getUsername(): string{
    return this._authService.getUsernameStored();
  }

  logout(): void {
    return this._authService.logout();
  }
}
