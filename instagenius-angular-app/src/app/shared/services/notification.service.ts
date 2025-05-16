// notification.service.ts - improved with RxJS
import { inject, Injectable, OnDestroy } from '@angular/core';
import { ToastModalComponent } from '../components/toast-modal/toast-modal.component';
import { EMPTY, Observable, Subject, Subscription, timer } from 'rxjs';
import { finalize, switchMap, takeUntil, tap } from 'rxjs/operators';
import { ToastData } from '../models/toast-data.model';
import { Dialog, DialogRef } from '@angular/cdk/dialog';
import { NoopScrollStrategy } from '@angular/cdk/overlay';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root',
})
export class NotificationService implements OnDestroy {
  private destroy$ = new Subject<void>();
  private activeDialogs: MatDialogRef<unknown, ToastModalComponent>[] = [];
  private matDialog = inject(MatDialog);

  showNotification(message: string, isSuccess = true, duration = 3000) {
    const dialogRef = this.matDialog.open(ToastModalComponent, {
      width: '400px',
      panelClass: isSuccess ? 'success-dialog' : 'error-dialog',
      disableClose: true,
      hasBackdrop: false,
      scrollStrategy: new NoopScrollStrategy(),
      position: {
        bottom: '25px',
        left: '25px',
      },
      data: {
        message: message,
        isSuccess: isSuccess,
        dismissible: true,
      } as ToastData,
    });

    this.activeDialogs.push(dialogRef);

    timer(duration)
      .pipe(
        takeUntil(dialogRef.afterClosed()),
        takeUntil(this.destroy$),
        tap(() => dialogRef.close()),
        finalize(() => {
          const index = this.activeDialogs.indexOf(dialogRef);
          if (index > -1) {
            this.activeDialogs.splice(index, 1);
          }
        })
      )
      .subscribe();

    return dialogRef;
  }

  showNotificationWithCompletion(
    message: string,
    isSuccess = true,
    duration = 3000
  ): Observable<any> {
    const dialogRef = this.showNotification(message, isSuccess, duration);
    return dialogRef.afterClosed();
  }

  closeAll(): void {
    this.activeDialogs.forEach((dialog) => dialog.close());
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();

    this.closeAll();
  }
}
