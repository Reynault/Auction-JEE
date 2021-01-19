import {Participation} from './participation';

export interface Auction{
  id: string;
  firstPrice: string;
  timeLimit: string;
  best: string;
  participations: Participation[];
}
