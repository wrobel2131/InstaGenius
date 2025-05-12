import { Component } from '@angular/core';
import { LandingHeaderComponent } from '../../components/landing-header/landing-header.component';
import { LandingFooterComponent } from '../../components/landing-footer/landing-footer.component';
import {
  LucideAngularModule,
  SparkleIcon,
  ArrowRightIcon,
  ArrowUpIcon,
  CheckCheckIcon,
  ZapIcon,
} from 'lucide-angular';

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [
    LandingHeaderComponent,
    LandingFooterComponent,
    LucideAngularModule,
  ],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css',
})
export class LandingPageComponent {
  isMenuOpen = false;
  activeAccordion: number | null = null;
  readonly sparkle = SparkleIcon;
  readonly arrowRight = ArrowRightIcon;
  readonly checkCheck = CheckCheckIcon;
  readonly zap = ZapIcon;
  readonly arrowUp = ArrowUpIcon;

  toggleAccordion(index: number): void {
    this.activeAccordion = this.activeAccordion === index ? null : index;
  }

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
        'Oferujemy kilka planów subskrypcyjnych, w tym darmowy plan z ograniczoną liczbą generowań miesięcznie. Plany premium zaczynają się od 19 zł/miesiąc i oferują zaawansowane funkcje oraz większą liczbę generowań.',
    },
    {
      question: 'Czy mogę zaplanować posty bezpośrednio z aplikacji?',
      answer:
        'Tak, w planach premium oferujemy integrację z Instagramem, która pozwala na planowanie i publikowanie postów bezpośrednio z naszej aplikacji.',
    },
  ];

  // testimonials = [
  //   {
  //     name: 'Karolina M.',
  //     role: 'Influencerka lifestyle',
  //     content:
  //       'InstaGenius odmienił moje podejście do contentu. Oszczędzam mnóstwo czasu, a moje zasięgi wzrosły o 40%!',
  //     avatar: 'KM',
  //   },
  //   {
  //     name: 'Marek W.',
  //     role: 'Właściciel małego biznesu',
  //     content:
  //       'Nie mam czasu na wymyślanie postów. Dzięki InstaGenius moja kawiarnia ma regularnie aktualizowany profil, który przyciąga nowych klientów.',
  //     avatar: 'MW',
  //   },
  //   {
  //     name: 'Anna K.',
  //     role: 'Social Media Manager',
  //     content:
  //       'Zarządzam 5 kontami jednocześnie. Ta aplikacja to prawdziwy game changer dla mojej pracy i kreatywności.',
  //     avatar: 'AK',
  //   },
  // ];

  onLogin() {
    console.log('login');
  }

  onRegister() {
    console.log('register');
  }

  scrollToTop(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
