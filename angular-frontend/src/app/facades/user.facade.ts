import {inject, Injectable} from "@angular/core";
import {UserApiService} from "../services/user-api.service";
import {UserStore} from "../stores/user.store";
import {LoadingStore} from "../stores/loading.store";
import {catchError, Observable, of, tap} from "rxjs";
import {UpdateUser, User} from "../models/user.model";

@Injectable({
    providedIn: 'root'
})
export class UserFacade {
    private userApiService: UserApiService = inject(UserApiService);
    private userStore: UserStore = inject(UserStore);
    private loadingStore: LoadingStore = inject(LoadingStore);
    private readonly LOADERS = {
        GET_USER: 'user.get',
        UPDATE_USER: 'user.update'
    };

    public loadingUser = this.loadingStore.getLoaderState(this.LOADERS.GET_USER);
    public updatingUser = this.loadingStore.getLoaderState(this.LOADERS.UPDATE_USER);
    public currentUser = this.userStore.user
    public error = this.userStore.error;

    getUser(): Observable<User | null> {
        this.loadingStore.startLoading(this.LOADERS.GET_USER);
        this.userStore.setError(null);

        return this.userApiService.getUserProfile().pipe(
            tap(user => {
                this.userStore.setUser(user);
                this.loadingStore.stopLoading(this.LOADERS.GET_USER);
            }),
            catchError(error => {
                this.userStore.setError(error.message || 'Failed to load user');
                this.loadingStore.stopLoading(this.LOADERS.GET_USER);
                return of(null);
            })
        );
    }

    updateUser(updatedUser: UpdateUser): Observable<User | null> {
        this.loadingStore.startLoading(this.LOADERS.UPDATE_USER);
        this.userStore.setError(null);

        return this.userApiService.updateUserProfile(updatedUser).pipe(
            tap(updatedUser => {
                this.userStore.setUser(updatedUser);
                this.loadingStore.stopLoading(this.LOADERS.UPDATE_USER);
            }),
            catchError(error => {
                this.userStore.setError(error.message || 'Failed to update user');
                this.loadingStore.stopLoading(this.LOADERS.UPDATE_USER);
                return of(null);
            })
        );
    }

    clearUser() {
        this.userStore.setUser(null);
        this.userStore.setError(null);
    }

}