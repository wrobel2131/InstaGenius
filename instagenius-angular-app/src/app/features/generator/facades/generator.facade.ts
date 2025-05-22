import { inject, Injectable } from '@angular/core';
import { GeneratorApiService } from '../services/generator-api.service';
import { GeneratorStore } from '../stores/generator.store';
import { catchError, finalize, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GeneratorFacade {
  private apiService = inject(GeneratorApiService);
  private store = inject(GeneratorStore);

  readonly options$ = this.store.options$;
  readonly graphicOptions$ = this.store.graphicOptions$;
  readonly descriptionOptions$ = this.store.descriptionOptions$;
  readonly formData$ = this.store.formData$;
  readonly loading$ = this.store.loading$;
  readonly error$ = this.store.error$;

  loadOptions(): void {
    this.store.setLoading(true);
    this.store.setError(null);

    this.apiService
      .getGenerationOptions()
      .pipe(
        tap((options) => this.store.setOptions(options)),
        catchError((error) => {
          this.store.setError('Failed to load generator options');
          console.error('Error loading options:', error);
          return of([]);
        }),
        finalize(() => this.store.setLoading(false))
      )
      .subscribe();
  }

  updateField(field: string, value: string): void {
    this.store.updateFormField(field, value);
  }

  resetForm(): void {
    this.store.resetForm();
  }

  generatePost(): void {
    this.store.setLoading(true);
    this.store.setError(null);

    console.log('In facade, calling api with this');
    this.apiService
      .generatePost(this.store.formData$())
      .pipe(
        catchError((error) => {
          this.store.setError('Failed to generate Instagram post');
          console.error('Error generating post:', error);
          return of(null);
        }),
        finalize(() => this.store.setLoading(false))
      )
      .subscribe((result) => {
        if (result) {
          console.log('Post generated successfully:', result);
        }
      });
  }
}
