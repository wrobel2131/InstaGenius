import { computed, inject, Injectable, signal } from '@angular/core';
import { GeneratorOption } from '../models/generation-option.model';
import { GeneratorFormData } from '../models/generator-form.model';

@Injectable({
  providedIn: 'root',
})
export class GeneratorStore {
  //state
  private options = signal<GeneratorOption[]>([]);
  private formData = signal<GeneratorFormData>({
    description: '',
  });
  private loading = signal<boolean>(false);
  private error = signal<string | null>(null);

  //exposed readable signals
  readonly options$ = this.options.asReadonly();
  readonly formData$ = this.formData.asReadonly();
  readonly loading$ = this.loading.asReadonly();
  readonly error$ = this.error.asReadonly();

  //some specific computed values
  readonly graphicOptions$ = computed(() =>
    this.options().filter((option) => option.category === 'graphic')
  );

  readonly descriptionOptions$ = computed(() =>
    this.options().filter((option) => option.category === 'description')
  );

  setOptions(options: GeneratorOption[]): void {
    this.options.set(options);

    // Initialize form data with default values
    const initialData: GeneratorFormData = {
      description: '',
    };

    options.forEach((option) => {
      initialData[option.field] = option.defaultValue;
    });

    this.formData.set(initialData);
  }

  updateFormField(field: string, value: string): void {
    this.formData.update((current) => ({
      ...current,
      [field]: value,
    }));
  }

  setLoading(isLoading: boolean): void {
    this.loading.set(isLoading);
  }

  setError(error: string | null): void {
    this.error.set(error);
  }

  resetForm(): void {
    const defaultValues: GeneratorFormData = {
      description: '',
    };

    this.options().forEach((option) => {
      defaultValues[option.field] = option.defaultValue;
    });

    this.formData.set(defaultValues);
  }
}
