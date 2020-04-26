import { Moment } from 'moment';
import { IProductos } from 'app/shared/model/productos.model';

export interface IHistorialOfertas {
  id?: number;
  valorProducto?: number;
  valorOferta?: number;
  fechaInicial?: Moment;
  fechaFinal?: Moment;
  productoId?: IProductos;
}

export class HistorialOfertas implements IHistorialOfertas {
  constructor(
    public id?: number,
    public valorProducto?: number,
    public valorOferta?: number,
    public fechaInicial?: Moment,
    public fechaFinal?: Moment,
    public productoId?: IProductos
  ) {}
}
