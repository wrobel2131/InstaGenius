import { Component, forwardRef, HostListener, Input } from '@angular/core';
import {
  ControlValueAccessor,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
} from '@angular/forms';
import {
  ChevronDownIcon,
  CheckIcon,
  LucideAngularModule,
} from 'lucide-angular';
import { DropdownOption } from '../../models/dropdown-option.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-select-dropdown',
  standalone: true,
  imports: [LucideAngularModule, CommonModule, ReactiveFormsModule],
  templateUrl: './select-dropdown.component.html',
  styleUrl: './select-dropdown.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectDropdownComponent),
      multi: true,
    },
  ],
})
export class SelectDropdownComponent implements ControlValueAccessor {
  @Input() option!: DropdownOption;
  @Input() placeholder = 'Select...';

  chevronDown = ChevronDownIcon;
  check = CheckIcon;
  isOpen = false;
  value: string = '';
  disabled = false;

  private onChange = (value: string) => {};
  private onTouched = () => {};

  toggleDropdown(): void {
    if (!this.disabled) {
      this.isOpen = !this.isOpen;
    }
  }

  selectValue(selectedValue: string): void {
    if (!this.disabled) {
      this.writeValue(selectedValue);
      this.onChange(selectedValue);
      this.onTouched();
      this.isOpen = false;
    }
  }

  getSelectedValue(): string {
    return this.value || this.placeholder;
  }

  isSelected(value: string): boolean {
    return this.value === value;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.dropdown-container')) {
      this.isOpen = false;
    }
  }

  writeValue(value: string): void {
    this.value = value || '';
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
}
