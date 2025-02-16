import { Component } from '@angular/core';
import {TranslocoModule} from "@jsverse/transloco";

@Component({
    selector: 'app-github-login-button',
    standalone: true,
    imports: [TranslocoModule],
    templateUrl: './github-login-button.component.html',
    styleUrl: './github-login-button.component.scss'
})
export class GithubLoginButtonComponent {}
