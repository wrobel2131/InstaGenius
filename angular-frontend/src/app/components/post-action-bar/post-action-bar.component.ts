import { Component, Input } from '@angular/core';
import { InstagramPost } from '../../models/instagram-post.model';
import { MatTooltipModule } from '@angular/material/tooltip';
import {TranslocoModule} from "@jsverse/transloco";

@Component({
    selector: 'app-post-action-bar',
    standalone: true,
    imports: [TranslocoModule, MatTooltipModule],
    templateUrl: './post-action-bar.component.html',
    styleUrl: './post-action-bar.component.scss'
})
export class PostActionBarComponent {
  @Input() post: InstagramPost | undefined = undefined;

  onCopyDescription(): void {
    alert('Copy to clipboard');
  }

  onDeletePost(): void {
    alert('Delete post');
  }

  onSaveImage(): void {
    alert('Save image');
  }

  onSaveTitle(): void {
    alert('Save title');
  }
}
