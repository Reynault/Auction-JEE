import { Injectable } from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import {TokenService} from '../services/token-service';

@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor{

  urlsToNotUse: Array<string>;

  constructor(private _token: TokenService) {

    this.urlsToNotUse = [
      '/register$',
      '/login$',
      '/articles$'
    ];

  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.isValidRequestForInterceptor(request.url)) {
      console.log('Ajout du token : ' + this._token.get().token);
      const token = this._token.get();

      const modifReq = request.clone({
        headers: request.headers
          .append(`login`, this._token.getUser())
          .append(`Authorization`, `Bearer ${token.token}`)
      });

      console.log(modifReq);
      return next.handle(modifReq);
    }else {
      console.log('Pas besoin du token');
      return next.handle(request);
    }
  }


  private isValidRequestForInterceptor(requestUrl: string): boolean {
    const positionIndicator = '/auction';
    const position = requestUrl.indexOf(positionIndicator);
    if (position > 0) {
      const destination: string = requestUrl.substr(position + positionIndicator.length);
      for (const address of this.urlsToNotUse) {
        console.log('Address : ' + address);
        console.log('Destination : ' + destination);
        console.log('Test : ' + new RegExp(address).test(destination));
        if (new RegExp(address).test(destination)) {
          return true;
        }
      }
    }
    return false;
  }
}
