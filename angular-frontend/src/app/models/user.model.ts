export interface User {
  id: string;
  email: string;
  username: string;
  firstName: string;
  lastName: string;
}

export type UpdateUser = {
  [K in 'username' | 'email' | 'firstName' | 'lastName']?: string | null;
};
