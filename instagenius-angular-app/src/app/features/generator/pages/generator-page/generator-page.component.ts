import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-generator-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './generator-page.component.html',
  styleUrl: './generator-page.component.css',
})
export class GeneratorPageComponent {
  activeTab: string = 'generator';
  openDropdown: boolean = false;
  showInfo: boolean = false;
  postContent: string = '';

  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }

  toggleDropdown(): void {
    this.openDropdown = !this.openDropdown;
  }

  toggleInfo(): void {
    this.showInfo = !this.showInfo;
  }

  generatePost(): void {
    // Logic for post generation will go here
    console.log('Generating post with content:', this.postContent);
  }

  clearHistory(): void {
    // Logic to clear generation history
    console.log('Clearing generation history');
  }

  editPost(postTitle: string): void {
    // Logic to edit a post from history
    console.log('Editing post:', postTitle);
    this.postContent = postTitle;
  }

  savePost(): void {
    // Logic to save the post
    console.log('Saving post');
  }

  editCurrentPost(): void {
    // Logic to edit current post
    console.log('Editing current post');
  }
}
