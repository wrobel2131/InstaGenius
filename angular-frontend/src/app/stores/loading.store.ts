import { Injectable, computed, signal } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class LoadingStore {
    private loadersMap = signal<Record<string, boolean>>({});

    // Global loading state
    public isLoading = computed(() =>
        Object.values(this.loadersMap()).some(isLoading => isLoading)
    );

    // Get loading state for a specific operation
    public getLoaderState(key: string) {
        return computed(() => this.loadersMap()[key] || false);
    }

    startLoading(key: string) {
        this.loadersMap.update(map => ({...map, [key]: true}));
    }

    stopLoading(key: string) {
        this.loadersMap.update(map => ({...map, [key]: false}));
    }
}