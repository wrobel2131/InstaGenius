import { DIALOG_DATA } from '@angular/cdk/dialog';
import { Component, inject } from '@angular/core';
import {
  BookmarkIcon,
  EllipsisIcon,
  HeartIcon,
  LucideAngularModule,
  MessageCircleIcon,
  SendIcon,
} from 'lucide-angular';

@Component({
  selector: 'app-gallery-post',
  imports: [LucideAngularModule],
  templateUrl: './gallery-post.component.html',
  styleUrl: './gallery-post.component.css',
})
export class GalleryPostComponent {
  post = inject(DIALOG_DATA);
  dots = EllipsisIcon;
  bookmark = BookmarkIcon;
  heart = HeartIcon;
  comment = MessageCircleIcon;
  send = SendIcon;
  username = 'Username';
  description =
    'Some description siema siema siema siema Some description siema siema siema siema Some description siema siema siema siema Some description siema siema siema siema Some description siema siema siema siema Some description siema siema siema siema Some description siema siema siema siema #elo #gy #viral';
  tags = '#elo #gy #viral';
}
