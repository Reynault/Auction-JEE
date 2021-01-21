import { Component, OnInit } from '@angular/core';
import {
  Validators,
  FormControl,
  FormGroup,
} from '@angular/forms';
import {AuthService} from '../shared/services/auth.service';
import {Router} from '@angular/router';
import {errorMessages} from '../shared/constants/error.messages';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private readonly _auth: AuthService,
              private _router: Router) {
    this._auth.logout();
    this._form = LoginComponent._buildForm();
  }

  get form(): FormGroup {
    return this._form;
  }

  get err(): string {
    return this._err;
  }
  private readonly _form: FormGroup;
  private _err: string;

  private static _buildForm(): FormGroup {
    return new FormGroup({
      login: new FormControl('', Validators.compose([
        Validators.required,
        Validators.maxLength(50)
      ])),
      pass: new FormControl('', Validators.compose([
        Validators.required,
        // PasswordValidators.hasUpperCase,
        // PasswordValidators.hasNumber,
        Validators.maxLength(200)
      ]))
    });
  }

  ngOnInit(): void {
  }

  submit(): void {
    if (this.form.valid) {
      console.log(this.form.value);
      this._auth.connect(this.form.value).subscribe(
        () => {
          this._auth.setUsernameStored(this.form.value.login);
          this._router.navigate(['/home']);
        },
        (error) => {
          console.log(error);
          switch (error.status) {
            case 401:
              this._err = errorMessages.wrongData;
              break;
            case 400:
              this._err = error.error.message;
              break;
            default:
              this._err = errorMessages.serverError;
              break;
          }
        }
      );
    }
  }
}
