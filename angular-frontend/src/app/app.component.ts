import {Component, OnInit, inject, OnDestroy} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { slider } from './route-animations';
import { TranslationService } from './services/translation.service';
import {UserFacade} from "./facades/user.facade";
import {Subscription} from "rxjs";

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [CommonModule, RouterLink, RouterOutlet],
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    animations: [slider]
})
export class AppComponent implements OnInit, OnDestroy {

  private translationService: TranslationService = inject(TranslationService);
  private userFacade: UserFacade = inject(UserFacade);
  private userFacadeSubscription!: Subscription;

  ngOnInit(): void {
    this.translationService.setDefaultLanguage();
    this.userFacadeSubscription = this.userFacade.getUser().subscribe();
  }

  prepareRoute(outlet: RouterOutlet) {
    return (
      outlet &&
      outlet.activatedRouteData &&
      outlet.activatedRouteData['animation']
    );
  }

    ngOnDestroy(): void {
      this.userFacadeSubscription.unsubscribe();
    }
}
