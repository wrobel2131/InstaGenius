import { CommonModule } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Post } from '../../../../shared/models/post.model';
import { GalleryTileComponent } from '../../components/gallery-tile/gallery-tile.component';

@Component({
  selector: 'app-gallery-page',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    GalleryTileComponent,
  ],
  templateUrl: './gallery-page.component.html',
  styleUrl: './gallery-page.component.css',
})
export class GalleryPageComponent implements OnInit {
  posts: Post[] = [];

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts() {
    // Przykładowe dane - zastąp swoim serwisem
    this.posts = [
      {
        id: '1',
        title: 'Inspirujący zachód słońca w górach',
        image:
          'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=400&fit=crop',
        description:
          'Odkryj magię natury dzięki temu przepięknemu widokowi. Każdy zachód słońca to nowa okazja do refleksji i wdzięczności za piękno, które nas otacza. #natura #góry #zachódsłońca #inspiracja',
        creationDate: '2024-05-20',
      },
      {
        id: '2',
        title: 'Nowoczesne wnętrze biura',
        image:
          'https://images.unsplash.com/photo-1497366216548-37526070297c?w=400&h=400&fit=crop',
        description:
          'Przestrzeń, która inspiruje do pracy i kreatywności. Minimalistyczny design spotyka się z funkcjonalnością. #biuro #design #workspace #produktywność',
        creationDate: '2024-05-19',
      },
      {
        id: '3',
        title: 'Pyszne śniadanie dla zdrowia',
        image:
          'https://images.unsplash.com/photo-1490645935967-10de6ba17061?w=400&h=400&fit=crop',
        description:
          'Rozpocznij dzień energią! Zdrowe składniki, piękna prezentacja i mnóstwo witamin. To jest przepis na udany poranek. #śniadanie #zdrowie #fit #energia',
        creationDate: '2024-05-18',
      },
      {
        id: '4',
        title: 'Miasto w nocnych światłach',
        image:
          'https://images.unsplash.com/photo-1514565131-fce0801e5785?w=400&h=400&fit=crop',
        description:
          'Kiedy miasto zasypia, światła budzą się do życia. Każde okno to czyjaś historia, każda ulica to droga do marzeń. #miasto #noc #światła #urban',
        creationDate: '2024-05-17',
      },
      {
        id: '5',
        title: 'Spokój w minimalistycznym salonie',
        image:
          'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400&h=400&fit=crop',
        description:
          'Mniej znaczy więcej. Przestrzeń, która pozwala oddychać i skupić się na tym, co naprawdę ważne. #minimalizm #wnętrza #spokój #dom',
        creationDate: '2024-05-16',
      },
      {
        id: '6',
        title: 'Świeże warzywa z ogrodu',
        image:
          'https://images.unsplash.com/photo-1540420773420-3366772f4999?w=400&h=400&fit=crop',
        description:
          'Prosto z grządki na talerz! Nie ma nic lepszego niż świeże, domowe warzywa pełne smaku i wartości odżywczych. #ogród #warzywa #zdrowie #ekologia',
        creationDate: '2024-05-15',
      },
    ];
  }
}
