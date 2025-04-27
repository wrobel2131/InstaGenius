import {Component, inject} from '@angular/core';
import { ExamplePostDesktopComponent } from '../example-post-desktop/example-post-desktop.component';
import { RouterLink } from '@angular/router';
import { ExamplePostMobileComponent } from '../example-post-mobile/example-post-mobile.component';
import {TranslocoModule} from "@jsverse/transloco";
import Keycloak from "keycloak-js";


@Component({
    selector: 'app-landing-page-content',
    standalone: true,
    imports: [
        ExamplePostDesktopComponent,
        ExamplePostMobileComponent,
        RouterLink,
        TranslocoModule,
    ],
    templateUrl: './landing-page-content.component.html',
    styleUrl: './landing-page-content.component.scss'
})
export class LandingPageContentComponent {
    private readonly keycloak: Keycloak = inject(Keycloak);
    onRegister(): void {
        console.log("Creating new account in....")
        this.keycloak.register().then(e => console.log("Created account in"));
    }

}
