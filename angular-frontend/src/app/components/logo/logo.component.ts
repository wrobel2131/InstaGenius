import { Component, Input } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-logo',
  templateUrl: './logo.component.svg',
  styleUrls: ['./logo.component.scss'],
})
export class LogoComponent {
  @Input() width = '100px';
  @Input() height = '100px';
}
