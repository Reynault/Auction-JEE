import {AbstractControl, ValidationErrors} from '@angular/forms';

export class EmailValidators{
  static email(control: AbstractControl): ValidationErrors | null {
    return /^.+@.+\..+$/.test(control.value) ? null : {
      email: true
    };
  }
}
