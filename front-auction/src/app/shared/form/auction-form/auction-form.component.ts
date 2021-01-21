import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuctionSend} from '../../interfaces/auction-send';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-auction-form',
  templateUrl: './auction-form.component.html',
  styleUrls: ['./auction-form.component.css']
})
export class AuctionFormComponent implements OnInit {

  private _id: string;

  /**
   * Component constructor
   */
  constructor(@Inject(MAT_DIALOG_DATA) data) {
    this._submit$ = new EventEmitter<AuctionSend>();
    this._cancel$ = new EventEmitter<void>();
    this._form = AuctionFormComponent._buildForm();
    this._id = data.id;
  }

  /**
   * Sets private property _model
   */
  @Input()
  set model(model: AuctionSend) {
    this._model = model;
  }

  /**
   * Returns private property _model
   */
  get model(): AuctionSend {
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
  get submit$(): EventEmitter<AuctionSend> {
    return this._submit$;
  }

  // private property to store model value
  private _model: AuctionSend;
  // private property to store cancel$ value
  private readonly _cancel$: EventEmitter<void>;
  // private property to store submit$ value
  private readonly _submit$: EventEmitter<AuctionSend>;
  // private property to store form value
  private readonly _form: FormGroup;

  /**
   * Function to build our form
   */
  private static _buildForm(): FormGroup {
    return new FormGroup({
      firstPrice: new FormControl(),
      timeLimit: new FormControl('', Validators.compose([
        Validators.required
      ])),
      id: new FormControl(),
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
  submit(auctionSend: AuctionSend): void {
    auctionSend.id = this._id;
    console.log('Emit : ' + this._id);
    this._submit$.emit(auctionSend);
  }

}
