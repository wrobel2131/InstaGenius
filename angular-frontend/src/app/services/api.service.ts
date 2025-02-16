import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { UpdateUser, User } from '../models/user.model';
import { Observable, map } from 'rxjs';
import {
  InstagramPost,
  InstagramPostTitleUpdate,
} from '../models/instagram-post.model';
import { GenerateOptions } from '../models/generated-post-options.model';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private http: HttpClient = inject(HttpClient);

  /* User endpoints*/

  getUser(userId: number): Observable<User> {
    console.log('getUser endpoint');
    console.log(userId);
    return this.http.get<User>('api/users/' + userId);
  }

  updateUser(updatedUser: UpdateUser): Observable<User> {
    console.log('updateUSer endpoint');
    //cos nie zmienia danych

    return this.http.put<User>('api/users/' + updatedUser.id, updatedUser);
  }

  /* Posts endpoint */

  getPosts(userId: number): Observable<InstagramPost[]> {
    console.log('getPosts endpoints');
    return this.http.get<InstagramPost[]>('api/posts?userId=' + userId);
  }

  getPost(postId: number): Observable<InstagramPost> {
    console.log('getPost endpoint');
    return this.http.get<InstagramPost>('api/posts/' + postId);
  }

  updatePostTitle(
    postId: number,
    updatedPost: InstagramPostTitleUpdate
  ): Observable<InstagramPost> {
    console.log('updating post title');
    return this.http.put<InstagramPost>('api/posts/' + postId, updatedPost);
  }

  generatePost(generateOptions: GenerateOptions): Observable<InstagramPost> {
    console.log('Generate endpoint');
    return this.http.post<InstagramPost>('api/posts/', generateOptions);
  }
}
