import { Moment } from 'moment';
import { ICategorias } from 'app/shared/model/categorias.model';
import { IProductos } from 'app/shared/model/productos.model';

export interface ICategoriaProducto {
  id?: number;
  categoria?: string;
  fecha?: Moment;
  catagoriaId?: ICategorias;
  productoId?: IProductos;
}

export class CategoriaProducto implements ICategoriaProducto {
  constructor(
    public id?: number,
    public categoria?: string,
    public fecha?: Moment,
    public catagoriaId?: ICategorias,
    public productoId?: IProductos
  ) {}
}
