import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IProductos } from 'app/shared/model/productos.model';

export interface IFavoritosProductos {
  id?: number;
  fecha?: Moment;
  idUser?: IUser;
  productoId?: IProductos;
}

export class FavoritosProductos implements IFavoritosProductos {
  constructor(public id?: number, public fecha?: Moment, public idUser?: IUser, public productoId?: IProductos) {}
}
