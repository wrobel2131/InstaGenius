import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectChange, MatSelectModule } from '@angular/material/select';
import { TranslationService } from '../../services/translation.service';
import { Language } from '../../models/language.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-pick-language-menu',
  standalone: true,
  imports: [MatFormFieldModule, MatSelectModule],
  templateUrl: './pick-language-menu.component.html',
  styleUrl: './pick-language-menu.component.scss',
})
export class PickLanguageMenuComponent implements OnInit, OnDestroy {
  private translationService: TranslationService = inject(TranslationService);

  languages!: Language[];
  currentLanguage!: Language;
  currentLanguageSubscription!: Subscription;

  ngOnInit(): void {
    this.languages = this.translationService.getAvailableLanguages();
    this.currentLanguageSubscription = this.translationService
      .getLanguageChangesObservable$()
      .subscribe(
        () =>
          (this.currentLanguage = this.translationService.getActiveLanguage())
      );
  }

  onChangeLanguage(event: MatSelectChange) {
    this.translationService.changeActiveLanguage(event.value);
  }

  getFlagClass(code: string): string {
    return 'fi fi-' + code;
  }

  ngOnDestroy() {
    this.currentLanguageSubscription.unsubscribe();
  }
}
