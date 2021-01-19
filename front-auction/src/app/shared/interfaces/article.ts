import {Category} from './category';
import {Auction} from './auction';

export interface Article{
  id: string;
  name: string;
  description: string;
  categories: Category[];
  auction: Auction;
}
