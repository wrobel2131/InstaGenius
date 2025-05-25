import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

interface Post {
  id: number;
  title: string;
  image: string;
  description: string;
  date: string;
  likes: number;
  comments: number;
  username: string;
  userAvatar: string;
}

@Component({
  selector: 'app-gallery-page',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './gallery-page.component.html',
  styleUrl: './gallery-page.component.css',
})
export class GalleryPageComponent implements OnInit {
  searchQuery: string = '';
  posts: Post[] = [];
  filteredPosts: Post[] = [];
  selectedPost: Post | null = null;
  isEditMode: boolean = false;
  isMobileEditMode: boolean = false;
  editedDescription: string = '';
  editedTitle: string = '';

  ngOnInit() {
    this.loadPosts();
    this.filteredPosts = this.posts;
  }

  loadPosts() {
    // Przykładowe dane - zastąp swoim serwisem
    this.posts = [
      {
        id: 1,
        title: 'Inspirujący zachód słońca w górach',
        image:
          'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=400&fit=crop',
        description:
          'Odkryj magię natury dzięki temu przepięknemu widokowi. Każdy zachód słońca to nowa okazja do refleksji i wdzięczności za piękno, które nas otacza. #natura #góry #zachódsłońca #inspiracja',
        date: '2024-05-20',
        likes: 157519,
        comments: 1886,
        username: 'jamalbrowne',
        userAvatar:
          'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=100&h=100&fit=crop&crop=face',
      },
      {
        id: 2,
        title: 'Nowoczesne wnętrze biura',
        image:
          'https://images.unsplash.com/photo-1497366216548-37526070297c?w=400&h=400&fit=crop',
        description:
          'Przestrzeń, która inspiruje do pracy i kreatywności. Minimalistyczny design spotyka się z funkcjonalnością. #biuro #design #workspace #produktywność',
        date: '2024-05-19',
        likes: 12847,
        comments: 234,
        username: 'drjamalbrowne',
        userAvatar:
          'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop&crop=face',
      },
      {
        id: 3,
        title: 'Pyszne śniadanie dla zdrowia',
        image:
          'https://images.unsplash.com/photo-1490645935967-10de6ba17061?w=400&h=400&fit=crop',
        description:
          'Rozpocznij dzień energią! Zdrowe składniki, piękna prezentacja i mnóstwo witamin. To jest przepis na udany poranek. #śniadanie #zdrowie #fit #energia',
        date: '2024-05-18',
        likes: 8934,
        comments: 156,
        username: 'healthylife_pl',
        userAvatar:
          'https://images.unsplash.com/photo-1494790108755-2616b612b641?w=100&h=100&fit=crop&crop=face',
      },
      {
        id: 4,
        title: 'Miasto w nocnych światłach',
        image:
          'https://images.unsplash.com/photo-1514565131-fce0801e5785?w=400&h=400&fit=crop',
        description:
          'Kiedy miasto zasypia, światła budzą się do życia. Każde okno to czyjaś historia, każda ulica to droga do marzeń. #miasto #noc #światła #urban',
        date: '2024-05-17',
        likes: 23456,
        comments: 567,
        username: 'cityvibes',
        userAvatar:
          'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100&h=100&fit=crop&crop=face',
      },
      {
        id: 5,
        title: 'Spokój w minimalistycznym salonie',
        image:
          'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400&h=400&fit=crop',
        description:
          'Mniej znaczy więcej. Przestrzeń, która pozwala oddychać i skupić się na tym, co naprawdę ważne. #minimalizm #wnętrza #spokój #dom',
        date: '2024-05-16',
        likes: 5678,
        comments: 89,
        username: 'minimal_home',
        userAvatar:
          'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=100&h=100&fit=crop&crop=face',
      },
      {
        id: 6,
        title: 'Świeże warzywa z ogrodu',
        image:
          'https://images.unsplash.com/photo-1540420773420-3366772f4999?w=400&h=400&fit=crop',
        description:
          'Prosto z grządki na talerz! Nie ma nic lepszego niż świeże, domowe warzywa pełne smaku i wartości odżywczych. #ogród #warzywa #zdrowie #ekologia',
        date: '2024-05-15',
        likes: 9876,
        comments: 234,
        username: 'garden_fresh',
        userAvatar:
          'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=100&h=100&fit=crop&crop=face',
      },
    ];
  }

  filterPosts() {
    if (!this.searchQuery.trim()) {
      this.filteredPosts = this.posts;
      return;
    }

    const query = this.searchQuery.toLowerCase();
    this.filteredPosts = this.posts.filter(
      (post) =>
        post.title.toLowerCase().includes(query) ||
        post.description.toLowerCase().includes(query)
    );
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('pl-PL', {
      day: 'numeric',
      month: 'long',
      year: 'numeric',
    });
  }

  openPost(post: Post) {
    this.selectedPost = post;
    this.editedDescription = post.description;
    this.editedTitle = post.title;
    this.isEditMode = false;
    this.isMobileEditMode = false;
    document.body.style.overflow = 'hidden';
  }

  closeModal() {
    this.selectedPost = null;
    this.isEditMode = false;
    this.isMobileEditMode = false;
    this.editedDescription = '';
    this.editedTitle = '';
    document.body.style.overflow = 'auto';
  }

  toggleEditMode() {
    this.isEditMode = !this.isEditMode;
    if (this.isEditMode && this.selectedPost) {
      this.editedTitle = this.selectedPost.title;
      this.editedDescription = this.selectedPost.description;
    }
  }

  toggleMobileEdit() {
    this.isMobileEditMode = !this.isMobileEditMode;
    if (this.isMobileEditMode && this.selectedPost) {
      this.editedTitle = this.selectedPost.title;
      this.editedDescription = this.selectedPost.description;
    }
  }

  saveMobileEdit() {
    if (this.selectedPost) {
      this.selectedPost.title = this.editedTitle;
      this.selectedPost.description = this.editedDescription;
      this.isMobileEditMode = false;
      console.log('Zapisano post (mobile):', {
        title: this.editedTitle,
        description: this.editedDescription,
      });
    }
  }

  cancelMobileEdit() {
    this.isMobileEditMode = false;
    if (this.selectedPost) {
      this.editedTitle = this.selectedPost.title;
      this.editedDescription = this.selectedPost.description;
    }
  }

  savePost() {
    if (this.selectedPost) {
      if (this.isEditMode) {
        this.selectedPost.title = this.editedTitle;
        this.selectedPost.description = this.editedDescription;
        this.isEditMode = false;
      }
      console.log('Zapisano cały post:', this.selectedPost);
    }
  }

  deletePost() {
    if (this.selectedPost && confirm('Czy na pewno chcesz usunąć ten post?')) {
      console.log('Usunięto post:', this.selectedPost.id);
      this.posts = this.posts.filter((p) => p.id !== this.selectedPost!.id);
      this.filterPosts();
      this.closeModal();
    }
  }

  onImageSelected(event: any) {
    const file = event.target.files[0];
    if (file && this.selectedPost) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        if (this.selectedPost) {
          this.selectedPost.image = e.target.result;
          console.log('Zaktualizowano zdjęcie posta');
        }
      };
      reader.readAsDataURL(file);
    }
    // Wyczyść input żeby można było wybrać ten sam plik ponownie
    event.target.value = '';
  }
}
