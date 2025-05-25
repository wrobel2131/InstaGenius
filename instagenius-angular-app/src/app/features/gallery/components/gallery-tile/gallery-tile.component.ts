import { Component, Inject, inject, Input } from '@angular/core';
import { Post } from '../../../../shared/models/post.model';
import { GalleryPostComponent } from '../gallery-post/gallery-post.component';
import { DOCUMENT } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { NoopScrollStrategy } from '@angular/cdk/overlay';

@Component({
  selector: 'app-gallery-tile',
  imports: [],
  templateUrl: './gallery-tile.component.html',
  styleUrl: './gallery-tile.component.css',
})
export class GalleryTileComponent {
  @Input() post!: Post;
  dialog = inject(MatDialog);

  onOpenPostDetails() {
    this.dialog.open(GalleryPostComponent, {
      data: this.post,
      width: '90vw',
      maxWidth: '90vw',
      height: '90vh',
    });
  }
}
