import { Component, OnInit, inject } from '@angular/core';
import { GalleryPostElementComponent } from '../gallery-post-element/gallery-post-element.component';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { InstagramPost } from '../../models/instagram-post.model';

@Component({
    selector: 'app-gallery-page',
    standalone: true,
    imports: [GalleryPostElementComponent, RouterOutlet, RouterLink],
    templateUrl: './gallery-page.component.html',
    styleUrl: './gallery-page.component.scss'
})
export class GalleryPageComponent {
  private router: Router = inject(Router);

  posts: InstagramPost[] = [];

  onDisplayPost(post: InstagramPost) {
    console.log(post);
    //TOOO probably set some signal responsible for saving state of selected post
    this.router.navigate(['dashboard', { outlets: { main: ['displayed'] } }]);
  }
}
