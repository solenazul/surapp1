import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ITableros } from 'app/shared/model/tableros.model';

export interface IInvitacionTablero {
  id?: number;
  estado?: boolean;
  fecha?: Moment;
  idUser?: IUser;
  tableroId?: ITableros;
}

export class InvitacionTablero implements IInvitacionTablero {
  constructor(public id?: number, public estado?: boolean, public fecha?: Moment, public idUser?: IUser, public tableroId?: ITableros) {
    this.estado = this.estado || false;
  }
}
