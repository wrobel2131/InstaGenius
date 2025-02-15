import { DestroyRef, Injectable, effect, inject, signal } from '@angular/core';
import { UpdateUser, User, UserLoginCredentials } from '../models/user.model';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { InstagramPost } from '../models/instagram-post.model';
import { ApiService } from './api.service';
import { catchError, filter, first, of, switchMap } from 'rxjs';
import { toSignal, toObservable } from '@angular/core/rxjs-interop';
import { GenerateOptions } from '../models/generated-post-options.model';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);
  private apiService: ApiService = inject(ApiService);

  //Needs to be changed to false as defualt
  isAuthenticated = signal<boolean>(true);

  isAuthenticatedEffect = effect(() => {
    if (!this.isAuthenticated()) {
      this.authService.clearLocalStorage();
    }
  });

  isLoginEditEnabled = signal<boolean>(false);

  userId = signal<number | undefined>(undefined);

  user = toSignal(
    toObservable(this.userId).pipe(
      filter((id): id is number => id !== undefined),
      switchMap((id) => this.apiService.getUser(id))
    )
  );

  posts = toSignal(
    toObservable(this.userId).pipe(
      filter((id): id is number => id !== undefined),
      switchMap((id) => this.apiService.getPosts(id))
    )
  );

  selectedPost = signal<InstagramPost | undefined>(undefined);

  setUserId(userId: number) {
    this.userId.set(userId);
  }

  setIsAuthenticated(isAuthenticated: boolean): void {
    this.isAuthenticated.set(isAuthenticated);
  }
  setSelectedPost(post: InstagramPost) {
    this.selectedPost.set(post);
  }

  /* Methods which are using set methods for signals */

  login(userLoginCredentials: UserLoginCredentials): void {
    this.authService.login(userLoginCredentials).subscribe((response) => {
      this.authService.setLocalStorage(response);
      this.setIsAuthenticated(true);
      this.router.navigate(['/dashboard']);
      this.setUserId(response.userId);
    });
  }

  updateUser(updatedUser: UpdateUser): void {
    this.apiService
      .updateUser(updatedUser)
      .subscribe((updatedUser) => {
        this.userId.set(updatedUser.id);
      })
      .unsubscribe();
  }

  generatePost(generateOptions: GenerateOptions): void {
    this.apiService
      .generatePost(generateOptions)
      .subscribe((generatedPost) => this.selectedPost.set(generatedPost))
      .unsubscribe();
  }

  logout(): void {
    this.setIsAuthenticated(false);
    this.router.navigate(['']);
  }
}
