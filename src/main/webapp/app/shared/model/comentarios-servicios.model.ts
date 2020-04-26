import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IProductos } from 'app/shared/model/productos.model';

export interface IComentariosServicios {
  id?: number;
  comentario?: string;
  fecha?: Moment;
  estado?: string;
  idUser?: IUser;
  productoId?: IProductos;
}

export class ComentariosServicios implements IComentariosServicios {
  constructor(
    public id?: number,
    public comentario?: string,
    public fecha?: Moment,
    public estado?: string,
    public idUser?: IUser,
    public productoId?: IProductos
  ) {}
}
