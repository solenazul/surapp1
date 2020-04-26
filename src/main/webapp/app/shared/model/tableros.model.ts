import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ICategoriaTablero } from 'app/shared/model/categoria-tablero.model';

export interface ITableros {
  id?: number;
  nombre?: string;
  fecha?: Moment;
  privado?: boolean;
  calificacion?: number;
  idUser?: IUser;
  categoriaId?: ICategoriaTablero;
}

export class Tableros implements ITableros {
  constructor(
    public id?: number,
    public nombre?: string,
    public fecha?: Moment,
    public privado?: boolean,
    public calificacion?: number,
    public idUser?: IUser,
    public categoriaId?: ICategoriaTablero
  ) {
    this.privado = this.privado || false;
  }
}
