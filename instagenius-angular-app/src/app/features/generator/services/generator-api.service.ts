import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Post } from '../../../shared/models/post.model';
import { GeneratorOption } from '../models/generation-option.model';
import { GeneratorFormData } from '../models/generator-form.model';

@Injectable({
  providedIn: 'root',
})
export class GeneratorApiService {
  private API_URL = 'api/generator/instagram';
  private httpClient = inject(HttpClient);

  getGenerationOptions(): Observable<GeneratorOption[]> {
    const instagramPostGeneratorOptions: GeneratorOption[] = [
      {
        category: 'graphic',
        field: 'visualStyle',
        label: 'Visual Style',
        values: [
          'minimalist',
          'vibrant',
          'vintage',
          'modern',
          'artistic',
          'photography',
        ],
        defaultValue: 'minimalist',
      },
      {
        category: 'graphic',
        field: 'colorScheme',
        label: 'Color Scheme',
        values: [
          'brand',
          'summer',
          'fall',
          'winter',
          'spring',
          'energetic',
          'calm',
          'professional',
          'playful',
        ],
        defaultValue: 'brand',
      },
      {
        category: 'graphic',
        field: 'layout',
        label: 'Layout',
        values: ['single', 'carousel', 'grid', 'split', 'overlay', 'framed'],
        defaultValue: 'single',
      },
      {
        category: 'graphic',
        field: 'graphicElements',
        label: 'Graphic Elements',
        values: ['icons', 'typography', 'product', 'people', 'nature'],
        defaultValue: 'typography',
      },
      {
        category: 'description',
        field: 'tone',
        label: 'Tone',
        values: [
          'professional',
          'conversational',
          'motivational',
          'humorous',
          'educational',
          'storytelling',
        ],
        defaultValue: 'conversational',
      },
      {
        category: 'description',
        field: 'length',
        label: 'Length',
        values: ['ultra-short', 'short', 'medium', 'long'],
        defaultValue: 'medium',
      },
      {
        category: 'description',
        field: 'structure',
        label: 'Structure',
        values: ['question', 'list', 'story', 'cta', 'quote'],
        defaultValue: 'story',
      },
      {
        category: 'description',
        field: 'hashtagQuantity',
        label: 'Hashtags',
        values: ['none', 'minimal', 'moderate', 'abundant'],
        defaultValue: 'moderate',
      },
      {
        category: 'description',
        field: 'emojiDensity',
        label: 'Emoji Density',
        values: ['none', 'minimal', 'moderate', 'abundant'],
        defaultValue: 'minimal',
      },
    ];

    return of(instagramPostGeneratorOptions);
  }

  generatePost(formData: GeneratorFormData): Observable<Post> {
    console.log('In api service, generating post with those formdata: ', {
      ...formData,
    });

    const post: Post = {
      id: '123123',
      description: 'some description # new #ig #koks',
      image: 'assets/image.png',
      title: 'My new journey to Wonderland',
      creationDate: '2024-05-20',
    };
    return of(post);
  }
}
