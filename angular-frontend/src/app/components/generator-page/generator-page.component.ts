import { Component, inject } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDividerModule } from '@angular/material/divider';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatExpansionModule } from '@angular/material/expansion';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {TranslocoModule} from "@jsverse/transloco";


@Component({
    selector: 'app-generator-page',
  standalone: true,
  imports: [
        MatFormFieldModule,
        MatDividerModule,
        MatInputModule,
        MatSelectModule,
        MatExpansionModule,
        ReactiveFormsModule,
        TranslocoModule,
    ],
    templateUrl: './generator-page.component.html',
    styleUrl: './generator-page.component.scss'
})
export class GeneratorPageComponent {
  private router: Router = inject(Router);
  private formBuilder: NonNullableFormBuilder = inject(NonNullableFormBuilder);

  graphicOptionsForm: FormGroup = this.formBuilder.group({
    model: ['', Validators.required],
    style: [{ value: '', disabled: true }],
    quality: [{ value: '', disabled: true }],
  });

  descriptionOptionsForm: FormGroup = this.formBuilder.group({
    model: ['', Validators.required],
  });

  optionsForm: FormGroup = this.formBuilder.group({
    graphicOptions: this.graphicOptionsForm,
    descriptionOptions: this.descriptionOptionsForm,
  });

  onGeneratePost() {
    if (this.optionsForm.valid) {
      console.log(this.optionsForm.value);
      this.router.navigate(['dashboard', { outlets: { main: ['displayed'] } }]);
    }
  }

  onGraphicModelOptionChange(modelValue: string) {
    if (modelValue === 'dall-e-3') {
      this.graphicOptionsForm.controls['quality'].enable();
      this.graphicOptionsForm.controls['style'].enable();
      this.graphicOptionsForm.controls['quality'].setValidators([
        Validators.required,
      ]);
      this.graphicOptionsForm.controls['style'].setValidators([
        Validators.required,
      ]);
      this.graphicOptionsForm.controls['quality'].updateValueAndValidity();
      this.graphicOptionsForm.controls['style'].updateValueAndValidity();
      console.log(this.graphicOptionsForm.value);
    } else {
      this.graphicOptionsForm.controls['quality'].disable();
      this.graphicOptionsForm.controls['style'].disable();
      this.graphicOptionsForm.controls['quality'].clearValidators();
      this.graphicOptionsForm.controls['style'].clearValidators();
      this.graphicOptionsForm.updateValueAndValidity();
      console.log(modelValue);
    }
  }
}
