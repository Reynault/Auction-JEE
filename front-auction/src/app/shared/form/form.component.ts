import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../services/auth.service';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {
  exists = false;
  submitted = false;

  linkReg =
    '(http://www.|https://www.|http://|https://)?[a-z0-9]+([-.]{1}[a-z0-9]+)*.[a-z]{2,5}(:[0-9]{1,5})?(/.*)';
  fileName;
  places = [1, 2, 3, 4];
  default = 1;
  file: File;

  form = this.fb.group({
    title: ['', Validators.required],
    text: ['', Validators.required],
    url: ['', [Validators.required, Validators.pattern(this.linkReg)]],
    // imageUrl: [null, [Validators.required, Validators.pattern(this.linkReg)]],
    order: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {

  }

  get nameControl(): FormControl {
    return this.form.get('title') as FormControl;
  }

  get textControl(): FormControl {
    return this.form.get('text') as FormControl;
  }

  get linkControl(): FormControl {
    return this.form.get('url') as FormControl;
  }

  get imageControl(): FormControl {
    return this.form.get('imageUrl') as FormControl;
  }

  get placeControl(): FormControl {
    return this.form.get('order') as FormControl;
  }

  openFileBrowser(event): void {
    event.preventDefault();
    const inp: HTMLElement = document.getElementById(
      'imageBrowser'
    ) as HTMLElement;
    const input = document.getElementById('imageBrowser') as HTMLInputElement;
    input.addEventListener('change', () => {
      const title = input.value.split(/\\|\//).pop();
      this.fileName = title.length > 20 ? title.substr(title.length - 20) : title;
    });
    inp.click();
  }

  imageChange(event): void {
    this.file = event.target.files[0];
  }

  createAdd(form: FormGroup): void {
    this.submitted = true;
    const { value, valid } = form;

    const add = {
      title: form.value.title,
      order: form.value.order - 1,
      text: form.value.text,
      url: form.value.url
    };

  }

  private reset(): void {
    this.form.reset();
    this.submitted = false;
    this.file = null;
    this.fileName = null;
  }
}
