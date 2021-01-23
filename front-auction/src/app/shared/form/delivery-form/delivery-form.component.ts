import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuctionSend} from '../../interfaces/auction-send';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Promotion} from '../../interfaces/promotion';
import {Address} from '../../interfaces/address';
import {A} from '@angular/cdk/keycodes';

@Component({
  selector: 'app-delivery-form',
  templateUrl: './delivery-form.component.html',
  styleUrls: ['./delivery-form.component.css']
})
export class DeliveryFormComponent implements OnInit {

  /**
   * Component constructor
   */
  constructor(@Inject(MAT_DIALOG_DATA) data) {
    this._submit$ = new EventEmitter<{promo: FormArray, address: Address}>();
    this._cancel$ = new EventEmitter<void>();
    this._form = DeliveryFormComponent._buildForm();
  }

  /**
   * Sets private property _model
   */
  @Input()
  set model(model) {
    this._model = model;
  }

  /**
   * Returns private property _model
   */
  get model(): {promo: Promotion[], address: Address} {
    return this._model;
  }

  /**
   * Returns private property _form
   */
  get form(): FormGroup {
    return this._form;
  }

  /**
   * Returns private property _cancel$
   */
  @Output('cancel')
  get cancel$(): EventEmitter<void> {
    return this._cancel$;
  }

  /**
   * Returns private property _submit$
   */
  @Output('submit')
  get submit$(): EventEmitter<{promo: FormArray, address: Address}> {
    return this._submit$;
  }

  // private property to store model value
  private _model: {promo: Promotion[], address: Address};
  // private property to store cancel$ value
  private readonly _cancel$: EventEmitter<void>;
  // private property to store submit$ value
  private readonly _submit$: EventEmitter<{promo: FormArray, address: Address}>;
  // private property to store form value
  private readonly _form: FormGroup;

  /**
   * Function to build our form
   */
  private static _buildForm(): FormGroup {
    return new FormGroup({
      myChoices: new FormArray([]),
      id: new FormControl(),
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
    });
  }

  /**
   * OnInit implementation
   */
  ngOnInit(): void {
  }

  /**
   * Function to emit event to cancel process
   */
  cancel(): void {
    this._cancel$.emit();
  }

  /**
   * Function to emit event to submit form and person
   */
  submit(): void {
    this._submit$.emit({promo : this._form.get('myChoices') as FormArray, address: this._form.get('address').value});
  }

  onCheckChange(event, id: string): void {
    const formArray: FormArray = this.form.get('myChoices') as FormArray;

    /* Selected */
    if (event.target.checked) {
      // Add a new control in the arrayForm
      formArray.push(new FormControl(this._model.promo.filter(p => p.id === id)));
    }
    /* unselected */
    else {
      // find the unselected element
      let i = 0;

      formArray.controls.forEach((ctrl: FormControl) => {
        if (ctrl.value[0].id === id) {
          // Remove the unselected element from the arrayForm
          formArray.removeAt(i);
          return;
        }

        i++;
      });
    }
  }
}
