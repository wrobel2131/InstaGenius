import { Component, OnInit, inject } from '@angular/core';
import { GalleryPostElementComponent } from '../gallery-post-element/gallery-post-element.component';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { UserDataService } from '../../services/user-data.service';
import { InstagramPost } from '../../models/instagram-post.model';

@Component({
  selector: 'app-gallery-page',
  standalone: true,
  imports: [GalleryPostElementComponent, RouterOutlet, RouterLink],
  templateUrl: './gallery-page.component.html',
  styleUrl: './gallery-page.component.scss',
})
export class GalleryPageComponent {
  private router: Router = inject(Router);
  private userDataService: UserDataService = inject(UserDataService);

  posts = this.userDataService.posts;

  onDisplayPost(post: InstagramPost) {
    console.log(post);
    this.userDataService.setSelectedPost(post);
    this.router.navigate(['dashboard', { outlets: { main: ['displayed'] } }]);
  }
}
