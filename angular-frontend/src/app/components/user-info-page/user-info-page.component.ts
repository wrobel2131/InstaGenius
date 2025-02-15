import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { TranslocoModule } from '@ngneat/transloco';
import { UserDataService } from '../../services/user-data.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UpdateUser, User } from '../../models/user.model';

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
  styleUrl: './user-info-page.component.scss',
})
export class UserInfoPageComponent {
  private userDataService: UserDataService = inject(UserDataService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  user = this.userDataService.user;

  isLoginEditEnabled = this.userDataService.isLoginEditEnabled;

  userDataForm = this.formBuilder.group({
    login: [{ value: '', disabled: !this.isLoginEditEnabled() }],
    email: [''],
    firstName: [''],
    lastName: [''],
  });

  userPasswordsForm = this.formBuilder.group({
    password: ['', Validators.required],
    confirmPassword: ['', Validators.required],
  });

  onUpgrade(): void {
    if (this.userDataForm.valid) {
      console.log(this.userDataForm.value);
      console.log(this.user());
      this.userDataService.updateUser(this.updateUser);
    }
  }

  onChangePassword(): void {
    if (this.userPasswordsForm.valid) {
      console.log(this.userPasswordsForm.value);
    }
  }

  private get updateUser(): UpdateUser {
    return {
      id: this.user()?.id!,
      login: this.userDataForm.controls.login.value!,
      email: this.userDataForm.controls.email.value!,
      firstName: this.userDataForm.controls.firstName.value!,
      lastName: this.userDataForm.controls.lastName.value!,
    };
  }
}
