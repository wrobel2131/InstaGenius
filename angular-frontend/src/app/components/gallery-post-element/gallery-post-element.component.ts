import { Component, Input, inject } from '@angular/core';
import { InstagramPost } from '../../models/instagram-post.model';
import {TranslocoModule} from "@jsverse/transloco";
import {UserFacade} from "../../facades/user.facade";

@Component({
    selector: 'app-gallery-post-element',
    standalone: true,
    imports: [TranslocoModule],
    templateUrl: './gallery-post-element.component.html',
    styleUrl: './gallery-post-element.component.scss'
})
export class GalleryPostElementComponent {
  @Input() post: InstagramPost | null = null;
  private readonly userFacade: UserFacade = inject(UserFacade);

  get user() {
      return this.userFacade.currentUser();
  }
}
