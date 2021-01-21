import {User} from './user';

export interface Participation{
  id: string;
  price: string;
  login?: string;
  bider: User;
}
