import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Promotion} from '../../interfaces/promotion';
import {Address} from '../../interfaces/address';
import {PromotionService} from '../../services/promotion.service';
import {ParticipationService} from '../../services/participation.service';
import {Article} from '../../interfaces/article';

@Component({
  selector: 'app-delivery-form',
  templateUrl: './delivery-form.component.html',
  styleUrls: ['./delivery-form.component.css']
})
export class DeliveryFormComponent implements OnInit {

  private _totalPrice: string;
  private _id: string;

  /**
   * Component constructor
   */
  constructor(@Inject(MAT_DIALOG_DATA) data,
              private _promotionService: PromotionService,
              private _participationService: ParticipationService) {
    this._submit$ = new EventEmitter<{promo: FormArray, address: Address, id: string}>();
    this._cancel$ = new EventEmitter<void>();
    this._form = DeliveryFormComponent._buildForm();
    this._id = data.id;
    this._participationService.getParticipation(data.id).subscribe((article: Article) => {
      this._totalPrice = article.auction.best.price;
    });
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
  get model(): {promo: Promotion[], address: Address, id: string} {
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
  get submit$(): EventEmitter<{promo: FormArray, address: Address, id: string}> {
    return this._submit$;
  }

  // private property to store model value
  private _model: {promo: Promotion[], address: Address, id: string};
  // private property to store cancel$ value
  private readonly _cancel$: EventEmitter<void>;
  // private property to store submit$ value
  private readonly _submit$: EventEmitter<{promo: FormArray, address: Address, id: string}>;
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
    this._submit$.emit({
      promo : this._form.get('myChoices') as FormArray,
      address: this._form.get('address').value,
      id: this._id
    });
  }


  get totalPrice(): string {
    return this._totalPrice;
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

    this._promotionService.calculatePromotions(this._id, formArray.value).subscribe((_) => {
      this._totalPrice = _.price;
    });

  }
}
