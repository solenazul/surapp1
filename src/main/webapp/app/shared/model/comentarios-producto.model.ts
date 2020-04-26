import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IProductos } from 'app/shared/model/productos.model';

export interface IComentariosProducto {
  id?: number;
  comentario?: string;
  fecha?: Moment;
  estado?: string;
  idUser?: IUser;
  productoId?: IProductos;
}

export class ComentariosProducto implements IComentariosProducto {
  constructor(
    public id?: number,
    public comentario?: string,
    public fecha?: Moment,
    public estado?: string,
    public idUser?: IUser,
    public productoId?: IProductos
  ) {}
}
