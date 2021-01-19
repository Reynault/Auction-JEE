import {Parameter} from './parameter';

export interface Promotion{
  id: string;
  description: string;
  parameters: Parameter[];
  daily: boolean;
  type: string;
}
