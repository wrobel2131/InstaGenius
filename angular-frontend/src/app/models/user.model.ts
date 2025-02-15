export interface User {
  id: number;
  email: string;
  login: string;
  firstName: string;
  lastName: string;
  coins: number;
}

export type UpdateUser = Pick<
  User,
  'id' | 'login' | 'email' | 'firstName' | 'lastName'
>;

export type UserLoginCredentials = Pick<User, 'login'> & { password: string };

export type UserRegisterCredentials = Pick<User, 'email' | 'login'>;
