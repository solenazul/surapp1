import { Moment } from 'moment';

export interface ICategorias {
  id?: number;
  categoria?: string;
  fecha?: Moment;
}

export class Categorias implements ICategorias {
  constructor(public id?: number, public categoria?: string, public fecha?: Moment) {}
}
