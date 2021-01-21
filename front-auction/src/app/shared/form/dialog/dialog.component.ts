import {Component, Inject, OnInit, Optional} from '@angular/core';
import {AuctionSend} from '../../interfaces/auction-send';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {


  /**
   * Component constructor
   */
  constructor(private _dialogRef: MatDialogRef<DialogComponent>, @Optional() @Inject(MAT_DIALOG_DATA) private _auctionSend: AuctionSend) {
  }

  /**
   * Returns person passed in dialog open
   */
  get auctionSend(): AuctionSend {
    return this._auctionSend;
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
  onSave(auctionSend: AuctionSend): void {
    this._dialogRef.close(auctionSend);
  }
}
