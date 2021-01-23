import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../shared/services/auth.service';
import {Router} from '@angular/router';
import {PasswordValidators} from '../shared/validators/password-validators';
import {errorMessages} from '../shared/constants/error.messages';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  private readonly _form: FormGroup;
  private _err: string;

  constructor(private readonly _auth: AuthService,
              private _router: Router) {
    this._form = RegisterComponent._buildForm();
  }

  private static _buildForm(): FormGroup {
    return new FormGroup({
      login: new FormControl('', Validators.compose([
        Validators.required,
        Validators.maxLength(50)
      ])),
      name: new FormControl('', Validators.compose([
        Validators.required,
        Validators.maxLength(50)
      ])),
      lastname: new FormControl('', Validators.compose([
        Validators.required,
        Validators.maxLength(50)
      ])),
      address: new FormGroup({
        street: new FormControl('', Validators.compose([
          Validators.maxLength(250)
        ])),
        city: new FormControl('', Validators.compose([
          Validators.maxLength(250)
        ])),
        code: new FormControl('', Validators.compose([
          Validators.maxLength(250)
        ])),
        country: new FormControl('', Validators.compose([
          Validators.maxLength(250)
        ])),
      }),
      pass: new FormControl('', Validators.compose([
        Validators.required,
        PasswordValidators.hasUpperCase,
        PasswordValidators.hasNumber,
        Validators.maxLength(200)
      ])),
      confirmation: new FormControl('',
        Validators.compose([
          Validators.required
        ]))
    }, {validators: PasswordValidators.checkConfirm});
  }

  ngOnInit(): void {
  }

  get form(): FormGroup {
    return this._form;
  }

  get err(): string {
    return this._err;
  }

  subscribe(): void {
    if (this.form.valid) {
      const u = this._form.value;
      delete u.confirmation;
      if (this.form.value.address.street === '' ||
        this.form.value.address.country === '' ||
        this.form.value.address.code === '' ||
        this.form.value.address.city === '') {
        delete u.address;
      }
      this._auth.subscribe(u).subscribe(
        () => {
          this._auth.setUsernameStored(this.form.value.login);
          this._router.navigate(['/home']);
        },
        error => {
          switch (error.error.statusCode) {
            case 409:
              this._err = errorMessages.userAlreadyExists;
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
