import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../shared/services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  submitted = false;
  error = false;

  form = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  get usernameControl(): FormControl {
    return this.form.get('username') as FormControl;
  }

  get passwordControl(): FormControl {
    return this.form.get('password') as FormControl;
  }

  // register(form: FormGroup): void {
  //   this.submitted = true;
  //   const { value, valid } = form;
  //   if (valid) {
  //     this.authService.register(value.username, value.password).subscribe(
  //       data => {
  //         this.form.reset();
  //         this.submitted = false;
  //         this.router.navigate(['/login']);
  //       },
  //       error => {
  //         this.error = true;
  //       }
  //     );
  //   }
  // }
}
