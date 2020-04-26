import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ITableros } from 'app/shared/model/tableros.model';
import { IProductos } from 'app/shared/model/productos.model';

export interface IContenidoTablero {
  id?: number;
  comentario?: string;
  fecha?: Moment;
  idUser?: IUser;
  tableroId?: ITableros;
  productoId?: IProductos;
}

export class ContenidoTablero implements IContenidoTablero {
  constructor(
    public id?: number,
    public comentario?: string,
    public fecha?: Moment,
    public idUser?: IUser,
    public tableroId?: ITableros,
    public productoId?: IProductos
  ) {}
}
