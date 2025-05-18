import { Component, inject } from '@angular/core';
import { LandingHeaderComponent } from '../../../../shared/components/landing-header/landing-header.component';
import { LandingFooterComponent } from '../../../../shared/components/landing-footer/landing-footer.component';
import {
  LucideAngularModule,
  SparkleIcon,
  ArrowRightIcon,
  ArrowUpIcon,
  CheckCheckIcon,
  ZapIcon,
  ChevronDownIcon,
  ChevronUpIcon,
  SparklesIcon,
  ImageIcon,
  MessageSquareIcon,
  LibraryIcon,
  CheckIcon,
} from 'lucide-angular';
import { CdkAccordionModule } from '@angular/cdk/accordion';
import Keycloak from 'keycloak-js';

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [
    LandingHeaderComponent,
    LandingFooterComponent,
    LucideAngularModule,
    CdkAccordionModule,
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css',
})
export class HomePageComponent {
  isMenuOpen = false;
  activeAccordion: number | null = null;
  readonly sparkle = SparkleIcon;
  readonly arrowRight = ArrowRightIcon;
  readonly checkCheck = CheckCheckIcon;
  readonly zap = ZapIcon;
  readonly arrowUp = ArrowUpIcon;
  readonly chevronDown = ChevronDownIcon;
  readonly chevronUp = ChevronUpIcon;
  readonly sparkles = SparklesIcon;
  readonly image = ImageIcon;
  readonly messageSquare = MessageSquareIcon;
  readonly library = LibraryIcon;
  readonly check = CheckIcon;
  private readonly keycloak = inject(Keycloak);

  faqItems = [
    {
      question: 'Jak działa InstaGenius?',
      answer:
        'InstaGenius wykorzystuje zaawansowane modele AI do generowania pomysłów na posty, grafiki oraz opisy. Wystarczy, że podasz temat lub branżę, a nasza sztuczna inteligencja stworzy spersonalizowane propozycje dostosowane do Twojego profilu.',
    },
    {
      question: 'Czy mogę edytować wygenerowane posty?',
      answer:
        'Oczywiście! Wszystkie wygenerowane posty możesz dowolnie edytować, dostosowując je do swoich potrzeb. Możesz zmieniać tekst, dobierać inne hashtagi lub modyfikować styl grafiki.',
    },
    {
      question: 'Ile kosztuje korzystanie z InstaGenius?',
      answer:
        'Korzystanie z InstaGenius jest darmowe na start – każdy użytkownik otrzymuje Y monet, a za dołączenie do newslettera dodatkowe Z monet. System oparty jest na płatności monetami, które możesz wykorzystać do generowania treści. Pakiety monet dostępne są już od X zł.',
    },
  ];

  onLogin(): void {
    console.log('Logging in....');
    this.keycloak.login().then((e) => console.log('Logged in'));
  }

  onRegister(): void {
    console.log('Creating new account in....');
    this.keycloak.register().then((e) => console.log('Created account in'));
  }
}
