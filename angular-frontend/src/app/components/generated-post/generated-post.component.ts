import { Component } from '@angular/core';
import { GalleryPostElementComponent } from '../gallery-post-element/gallery-post-element.component';
import { PostActionBarComponent } from '../post-action-bar/post-action-bar.component';

@Component({
  selector: 'app-generated-post',
  standalone: true,
  imports: [GalleryPostElementComponent, PostActionBarComponent],
  templateUrl: './generated-post.component.html',
  styleUrl: './generated-post.component.scss',
})
export class GeneratedPostComponent {}
