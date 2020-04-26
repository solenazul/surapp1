import { Moment } from 'moment';
import { IProductos } from 'app/shared/model/productos.model';

export interface ICategoriaTablero {
  id?: number;
  categoria?: string;
  fecha?: Moment;
  productoId?: IProductos;
}

export class CategoriaTablero implements ICategoriaTablero {
  constructor(public id?: number, public categoria?: string, public fecha?: Moment, public productoId?: IProductos) {}
}
