import { Component, inject } from '@angular/core';
import { BrandSectionComponent } from '../brand-section/brand-section.component';
import { MatDividerModule } from '@angular/material/divider';
import { RouterLink } from '@angular/router';
import { LogoComponent } from '../logo/logo.component';
import { MobileFooterDarkComponent } from '../mobile-footer-dark/mobile-footer-dark.component';
import { TranslocoModule } from '@ngneat/transloco';
import { PickLanguageMenuComponent } from '../pick-language-menu/pick-language-menu.component';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [
    BrandSectionComponent,
    MobileFooterDarkComponent,
    LogoComponent,
    MatDividerModule,
    PickLanguageMenuComponent,
    RouterLink,
    TranslocoModule,
  ],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.scss',
})
export class RegisterPageComponent {
  private formBuilder: FormBuilder = inject(FormBuilder);

  registerForm = this.formBuilder.group({
    email: ['', Validators.required],
    login: ['', Validators.required],
    password: ['', Validators.required],
    confirmPassword: ['', Validators.required],
  });

  onRegister(): void {
    if (this.registerForm.valid) {
      console.log(this.registerForm.value);
    }
  }
}
