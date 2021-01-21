import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from './shared/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front-auction';

  constructor(private _router: Router, private _authService: AuthService) {
  }
  isLogin(): boolean{
    return this._authService.hasStoredToken();
  }
}
