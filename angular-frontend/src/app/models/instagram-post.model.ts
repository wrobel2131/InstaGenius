export interface InstagramPost {
  id: number;
  userId: number;
  title: string;
  imageUrl: string;
  description: string;
}

export type InstagramPostTitleUpdate = Pick<InstagramPost, 'title'>;
