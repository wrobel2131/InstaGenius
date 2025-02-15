import { Component } from '@angular/core';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
  selector: 'app-google-login-button',
  standalone: true,
  imports: [TranslocoModule],
  templateUrl: './google-login-button.component.html',
  styleUrl: './google-login-button.component.scss',
})
export class GoogleLoginButtonComponent {}
