import { Language } from './language.model';

export const SUPPORTED_LANGUAGES: Language[] = [
  {
    code: 'us',
    name: 'USA',
  },
  {
    code: 'pl',
    name: 'Polish',
  },
];

export const DEFAULT_LANGUAGE: Language = SUPPORTED_LANGUAGES[0];
