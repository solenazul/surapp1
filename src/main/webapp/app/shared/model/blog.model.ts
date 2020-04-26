import { IUser } from 'app/core/user/user.model';

export interface IBlog {
  id?: number;
  titulo?: string;
  contenido?: any;
  imagenContentType?: string;
  imagen?: any;
  video?: string;
  calificacion?: number;
  lecturas?: number;
  idUser?: IUser;
}

export class Blog implements IBlog {
  constructor(
    public id?: number,
    public titulo?: string,
    public contenido?: any,
    public imagenContentType?: string,
    public imagen?: any,
    public video?: string,
    public calificacion?: number,
    public lecturas?: number,
    public idUser?: IUser
  ) {}
}
