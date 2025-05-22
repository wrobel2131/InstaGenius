import { Component, Input, TemplateRef, ContentChild } from '@angular/core';
import { CircleHelpIcon, LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-generator-card',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './generator-card.component.html',
  styleUrl: './generator-card.component.css',
})
export class GeneratorCardComponent {
  @Input() title: string = '';
  @Input() hint: string | null = null;
  hintIcon = CircleHelpIcon;
  showTooltip = false;
}
