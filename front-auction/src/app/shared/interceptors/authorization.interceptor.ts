import { Injectable } from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {TokenService} from '../services/token-service';
import {Router} from '@angular/router';
import {catchError} from 'rxjs/operators';

@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor{

  urlsToNotUse: Array<string>;

  constructor(private _token: TokenService, private _router: Router) {

    this.urlsToNotUse = [
      '/register$',
      '/login$',
      '/articles$',
      '/articles/[0-9]*$',
      '/promotions$'
    ];

  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.isValidRequestForInterceptor(request.url)) {
      const token = this._token.get();
      const modifReq = request.clone({
        headers: request.headers
          .append(`login`, this._token.getUser())
          .append(`Authorization`, `Bearer ${token.token}`)
      });

      console.log(modifReq);
      return this.returnRequest(modifReq, next);
    }else {
      return this.returnRequest(request, next);
    }
  }


  private isValidRequestForInterceptor(requestUrl: string): boolean {
    const positionIndicator = '/auction';
    const position = requestUrl.indexOf(positionIndicator);
    if (position > 0) {
      const destination: string = requestUrl.substr(position + positionIndicator.length);
      for (const address of this.urlsToNotUse) {
        if (new RegExp(address).test(destination)) {
          return true;
        }
      }
    }
    return false;
  }


  returnRequest(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error) => {
        let handled = false;
        // console.error(error);
        if (error instanceof HttpErrorResponse) {
          if (error.error instanceof ErrorEvent) {
          } else {
            switch (error.status) {
              case 401:     // forbidden
                this._router.navigateByUrl('/login');
                handled = true;
                break;
            }
          }
        }

        if (handled) {
          return of(error);
        } else {
          return throwError(error);
        }

      })
    );
  }
}
