import {
  trigger,
  state,
  style,
  transition,
  animate,
} from '@angular/animations';
import { DIALOG_DATA, DialogRef } from '@angular/cdk/dialog';
import { CommonModule } from '@angular/common';
import { Component, Inject, inject, OnInit } from '@angular/core';
import {
  CheckIcon,
  LucideAngularModule,
  ShieldAlertIcon,
  XIcon,
} from 'lucide-angular';
import { ToastData } from '../../models/toast-data.model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-toast-modal',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './toast-modal.component.html',
  styleUrl: './toast-modal.component.css',
  animations: [
    trigger('progressAnimation', [
      state(
        'start',
        style({
          width: '100%',
        })
      ),
      state(
        'end',
        style({
          width: '0%',
        })
      ),
      transition('start => end', [animate('{{duration}}ms linear')], {
        params: { duration: 3000 },
      }),
    ]),
  ],
})
export class ToastModalComponent implements OnInit {
  progressState = 'start';
  alert = ShieldAlertIcon;
  check = CheckIcon;
  close = XIcon;

  constructor(
    public dialogRef: MatDialogRef<any, ToastModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ToastData
  ) {
    this.data = {
      ...{
        duration: 3000,
        dismissible: true,
      },
      ...data,
    };
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.progressState = 'end';
    }, 50);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
