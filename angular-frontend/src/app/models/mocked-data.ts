import { InstagramPost } from './instagram-post.model';
import { User } from './user.model';

export type UserDb = User & { password: string };
export type InstagramPostDb = InstagramPost;

export interface InMemoryDb {
  users: UserDb[];
  posts: InstagramPostDb[];
}
