import {Component, Inject, OnInit, Optional} from '@angular/core';
import {AuctionSend} from '../../interfaces/auction-send';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Promotion} from '../../interfaces/promotion';
import {Address} from '../../interfaces/address';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog-delivery.component.html',
  styleUrls: ['./dialog-delivery.component.css']
})
export class DialogDeliveryComponent implements OnInit {


  /**
   * Component constructor
   */
  constructor(private _dialogRef: MatDialogRef<DialogDeliveryComponent>,
              @Optional() @Inject(MAT_DIALOG_DATA) private _promotions: {promo: Promotion[], address: Address, id: string}) {
  }

  /**
   * Returns person passed in dialog open
   */
  get promotions(): {promo: Promotion[], address: Address, id: string} {
    return this._promotions;
  }

  /**
   * OnInit implementation
   */
  ngOnInit(): void {
  }

  /**
   * Function to cancel the process and close the modal
   */
  onCancel(): void {
    this._dialogRef.close();
  }

  /**
   * Function to close the modal and send person to parent
   */
  onSave(promotions: Promotion[]): void {
    this._dialogRef.close(promotions);
  }
}
