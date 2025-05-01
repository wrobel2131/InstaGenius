import {Injectable, signal} from "@angular/core";
import {User} from "../models/user.model";

@Injectable({
    providedIn: 'root'
})
export class UserStore {
    private userSignal = signal<User | null>(null);
    private errorSignal = signal<string | null>(null);

    public user = this.userSignal.asReadonly();
    public error = this.errorSignal.asReadonly();

    setUser(user: User | null): void {
        this.userSignal.set(user);
    }

    updateUser(updates: Partial<User>) {
        this.userSignal.update(user => {
            if (!user) return null;
            return { ...user, ...updates };
        });
    }

    setError(error: string | null) {
        this.errorSignal.set(error);
    }
}
