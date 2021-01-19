import { Injectable } from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import {TokenService} from '../services/token-service';

@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor{

  urlsToNotUse: Array<string>;

  constructor(private _token: TokenService) {

    this.urlsToNotUse = [
      '/register',
      '/login'
    ];

  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.isValidRequestForInterceptor(request.url)) {
      const token = this._token.get();
      request = request.clone({
        url: request.url,
        setHeaders: {
          Authorization: `Bearer ${token.token}`,
          login: this._token.getUser()
        }
      });

      console.log(request);
      return next.handle(request);
    }
    return next.handle(request);
  }


  private isValidRequestForInterceptor(requestUrl: string): boolean {
    const positionIndicator = '/auction';
    const position = requestUrl.indexOf(positionIndicator);
    console.log(requestUrl.indexOf(positionIndicator));
    if (position > 0) {
      const destination: string = requestUrl.substr(position + positionIndicator.length);
      console.log(destination);
      for (const address of this.urlsToNotUse) {
        console.log('Address : ' + address);
        console.log('Destination : ' + destination);
        console.log('Test : ' + new RegExp(address).test(destination));
        if (new RegExp(address).test(destination)) {
          return false;
        }
      }
    }
    return true;
  }
}
