import {Address} from './address';

export interface User{
  login: string;
  name: string;
  lastname: string;
  pass?: string;
  address?: Address;
}
