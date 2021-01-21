import {Address} from './address';
import {ArticleToDeliver} from './article-to-deliver';

export interface Delivery{
  id: string;
  step: string;
  price: string;
  address: Address;
  article: ArticleToDeliver;
}
