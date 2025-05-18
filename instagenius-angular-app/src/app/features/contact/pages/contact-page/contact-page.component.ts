import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { LandingHeaderComponent } from '../../../../shared/components/landing-header/landing-header.component';
import { LandingFooterComponent } from '../../../../shared/components/landing-footer/landing-footer.component';
import {
  LucideAngularModule,
  ArrowUpIcon,
  ChevronDownIcon,
  ChevronUpIcon,
  MailIcon,
  PhoneIcon,
  MapPinCheckIcon,
  FacebookIcon,
  InstagramIcon,
  LinkedinIcon,
  SendIcon,
} from 'lucide-angular';
import { CdkAccordionModule } from '@angular/cdk/accordion';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastModalComponent } from '../../../../shared/components/toast-modal/toast-modal.component';
import { Dialog, DIALOG_DATA, DialogModule } from '@angular/cdk/dialog';
import { NotificationService } from '../../../../shared/services/notification.service';

@Component({
  selector: 'app-contact-page',
  standalone: true,
  imports: [
    LandingHeaderComponent,
    LandingFooterComponent,
    LucideAngularModule,
    CdkAccordionModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    CommonModule,
    DialogModule,
  ],
  templateUrl: './contact-page.component.html',
  styleUrl: './contact-page.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ContactPageComponent implements OnInit {
  private fb: FormBuilder = inject(FormBuilder);
  private notificationService = inject(NotificationService);

  isMenuOpen = false;
  activeAccordion: number | null = null;
  readonly email = MailIcon;
  readonly address = MapPinCheckIcon;
  readonly phone = PhoneIcon;
  readonly arrowUp = ArrowUpIcon;
  readonly chevronDown = ChevronDownIcon;
  readonly send = SendIcon;
  readonly chevronUp = ChevronUpIcon;
  readonly instagram = InstagramIcon;
  readonly linkedin = LinkedinIcon;
  readonly facebook = FacebookIcon;

  messageForm!: FormGroup;

  faqItems = [
    {
      question: 'W jakich godzinach dostępne jest wsparcie?',
      answer:
        'Nasz zespół wsparcia jest dostępny od poniedziałku do piątku w godzinach 9:00-17:00. W soboty odpowiadamy w godzinach 10:00-17:00, a niedziele: 10:00-15:00',
    },
    {
      question: 'Jaki jest czas oczekiwania?',
      answer:
        'Staramy się odpowiadać na wszystkie zapytania w ciągu 24 godzin roboczych.',
    },
  ];

  ngOnInit(): void {
    this.messageForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', Validators.required],
      topic: ['', Validators.required],
      message: ['', Validators.required],
      agreement: [false, Validators.required],
    });
  }

  onLogin() {
    console.log('login');
  }

  onRegister() {
    console.log('register');
  }

  onSubmit() {
    if (
      this.messageForm.valid &&
      this.messageForm.controls['agreement'].value == true
    ) {
      console.log('Service call');
      this.notificationService.showNotification(
        'Message sent successfully!',
        true,
        3000
      );
      this.messageForm.reset();
    } else {
      this.notificationService.showNotification(
        'Fill the fields!',
        false,
        3000
      );
    }
  }
}
