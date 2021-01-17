import { Component, OnInit } from '@angular/core';
import {
  Validators,
  FormBuilder,
  FormControl,
  FormGroup,
} from '@angular/forms';
import {AuthService} from '../shared/services/auth.service';
import {Router} from '@angular/router';
import {PasswordValidators} from '../shared/validators/password-validators';
import {errorMessages} from '../shared/constants/error.messages';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private readonly _auth: AuthService,
              private _router: Router) {
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
      username: new FormControl('', Validators.compose([
        Validators.required,
        Validators.maxLength(50)
      ])),
      password: new FormControl('', Validators.compose([
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
      console.log(this._auth.connect(this.form.value).subscribe(
        (data) => {
          console.log(data);
          this._router.navigate(['/home']);
        },
        (error) => {
          console.log(error);
          switch (error.status) {
            case 401:
              this._err = errorMessages.wrongData;
              break;
            default:
              this._err = errorMessages.serverError;
              break;
          }
        }
      ));
    }
  }
}
