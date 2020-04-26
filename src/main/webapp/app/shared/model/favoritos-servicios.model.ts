import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IServicios } from 'app/shared/model/servicios.model';

export interface IFavoritosServicios {
  id?: number;
  fecha?: Moment;
  idUser?: IUser;
  productoId?: IServicios;
}

export class FavoritosServicios implements IFavoritosServicios {
  constructor(public id?: number, public fecha?: Moment, public idUser?: IUser, public productoId?: IServicios) {}
}
