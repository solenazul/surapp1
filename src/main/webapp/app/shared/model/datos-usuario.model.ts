import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IPais } from 'app/shared/model/pais.model';

export interface IDatosUsuario {
  id?: number;
  fechaNacimiento?: Moment;
  genero?: string;
  telefono?: number;
  pais?: string;
  ciudad?: string;
  direccion?: string;
  imageContentType?: string;
  image?: any;
  idUser?: IUser;
  nacionalidad?: IPais;
}

export class DatosUsuario implements IDatosUsuario {
  constructor(
    public id?: number,
    public fechaNacimiento?: Moment,
    public genero?: string,
    public telefono?: number,
    public pais?: string,
    public ciudad?: string,
    public direccion?: string,
    public imageContentType?: string,
    public image?: any,
    public idUser?: IUser,
    public nacionalidad?: IPais
  ) {}
}
