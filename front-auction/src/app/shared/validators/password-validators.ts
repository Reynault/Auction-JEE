import {AbstractControl, FormGroup, ValidationErrors} from '@angular/forms';

export class PasswordValidators {

  static hasUpperCase(control: AbstractControl): ValidationErrors | null {
    return /^.*[A-Z]+.*$/.test(control.value) ? null : {
      hasUpperCase: true
    };
  }

  static hasNumber(control: AbstractControl): ValidationErrors | null {
    return /^.*\d+.*$/.test(control.value) ? null : {
      hasNumber: true
    };
  }

  static checkConfirm(form: FormGroup): ValidationErrors | null {
    return (form.get('pass').value === form.get('confirmation').value) ? null : {
      checkConfirm: true
    };
  }
}
