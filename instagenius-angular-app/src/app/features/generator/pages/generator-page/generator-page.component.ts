import { CommonModule } from '@angular/common';
import { Component, effect, inject, OnInit } from '@angular/core';
import { GeneratorCardComponent } from '../../components/generator-card/generator-card.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { GeneratorFacade } from '../../facades/generator.facade';
import { GeneratorOption } from '../../models/generation-option.model';
import {
  CheckIcon,
  ChevronDownIcon,
  LucideAngularModule,
} from 'lucide-angular';
import { SelectDropdownComponent } from '../../components/select-dropdown/select-dropdown.component';

@Component({
  selector: 'app-generator-page',
  standalone: true,
  imports: [
    CommonModule,
    GeneratorCardComponent,
    ReactiveFormsModule,
    LucideAngularModule,
    SelectDropdownComponent,
  ],
  templateUrl: './generator-page.component.html',
  styleUrl: './generator-page.component.css',
})
export class GeneratorPageComponent implements OnInit {
  chevronDown = ChevronDownIcon;
  check = CheckIcon;

  form!: FormGroup;
  private fb = inject(FormBuilder);
  private facade = inject(GeneratorFacade);

  readonly graphicOptions$ = this.facade.graphicOptions$;
  readonly descriptionOptions$ = this.facade.descriptionOptions$;
  readonly options$ = this.facade.options$;
  readonly formData$ = this.facade.formData$;
  readonly loading$ = this.facade.loading$;
  readonly error$ = this.facade.error$;

  constructor() {
    effect(() => {
      const options = this.options$();
      if (options.length > 0) {
        this.initFormControls(options);
      }
    });

    effect(() => {
      const formData = this.formData$();
      if (this.form && Object.keys(formData).length > 0) {
        // Update form values without triggering valueChanges
        this.form.patchValue(formData, { emitEvent: false });
      }
    });
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      description: ['', Validators.required],
    });

    this.facade.loadOptions();

    this.setupFormValueChanges();
  }

  private setupFormValueChanges(): void {
    this.form.valueChanges.subscribe((values) => {
      Object.keys(values).forEach((field) => {
        if (values[field] !== null && values[field] !== undefined) {
          this.facade.updateField(field, values[field]);
        }
      });
    });
  }

  private initFormControls(options: GeneratorOption[]): void {
    const currentValues = this.form.value;

    const formConfig: { [key: string]: any } = {
      description: [currentValues.description || '', Validators.required],
    };

    options.forEach((option) => {
      const value =
        currentValues[option.field] !== undefined
          ? currentValues[option.field]
          : option.defaultValue;

      formConfig[option.field] = [value];
    });

    this.form = this.fb.group(formConfig);

    this.setupFormValueChanges();
  }

  onSubmit(): void {
    console.log('submitting...');
    if (this.form.valid) {
      this.facade.generatePost();
    } else {
      Object.keys(this.form.controls).forEach((key) => {
        const control = this.form.get(key);
        control?.markAsTouched();
      });
    }
  }

  resetForm(): void {
    this.facade.resetForm();
  }
}
