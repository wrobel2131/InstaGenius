import { CommonModule } from '@angular/common';
import {Component, effect, inject, OnInit, signal} from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UpdateUser } from '../../models/user.model';
import {TranslocoModule} from "@jsverse/transloco";
import {UserFacade} from "../../facades/user.facade";
import {Subject, takeUntil} from "rxjs";

@Component({
    selector: 'app-user-info-page',
    standalone: true,
    imports: [
        TranslocoModule,
        MatDividerModule,
        CommonModule,
        ReactiveFormsModule,
    ],
    templateUrl: './user-info-page.component.html',
    styleUrl: './user-info-page.component.scss'
})
export class UserInfoPageComponent {
  private formBuilder: FormBuilder = inject(FormBuilder);
  private userFacade: UserFacade = inject(UserFacade);


  isLoginEditEnabled = false;
  private destroy$ = new Subject<void>();

  userDataForm = this.formBuilder.group({
    username: [{ value: '', disabled: !this.isLoginEditEnabled }],
    email: [''],
    firstName: [''],
    lastName: [''],
  });

    constructor() {
        effect(() => {
            const user = this.userFacade.currentUser();
            if (user) {
                console.log("effect on init, user: {}", user)
                this.userDataForm.patchValue({
                    username: user?.username,
                    email: user?.email,
                    firstName: user?.firstName,
                    lastName: user?.lastName,
                }, { emitEvent: false });
            }
        });
    }

  onUpgrade(): void {
    if (this.userDataForm.valid) {
      console.log(this.userDataForm.value);
      console.log("Updating user");
      const updateUser: UpdateUser = {
          username: this.userDataForm.value.username,
          email: this.userDataForm.value.email,
          firstName: this.userDataForm.value.firstName,
          lastName: this.userDataForm.value.lastName,
      }
      console.log(updateUser)
      this.userFacade.updateUser(updateUser)
          .pipe(takeUntil(this.destroy$))
          .subscribe(
              {
                  next: (updatedUser) => {
                      if (updatedUser) {
                          console.log("updated user");
                      }
                  }
              }
          );
    }
  }

  get updatingUser() {
        return this.userFacade.updatingUser();
  }

  ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}
