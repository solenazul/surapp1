import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IModelo3D {
  id?: number;
  nombre?: string;
  urt?: string;
  colider?: string;
  textureA?: string;
  textureBC?: string;
  textureN?: string;
  textureR?: string;
  shadow?: string;
  acutalizado?: boolean;
  fecha?: Moment;
  idUser?: IUser;
}

export class Modelo3D implements IModelo3D {
  constructor(
    public id?: number,
    public nombre?: string,
    public urt?: string,
    public colider?: string,
    public textureA?: string,
    public textureBC?: string,
    public textureN?: string,
    public textureR?: string,
    public shadow?: string,
    public acutalizado?: boolean,
    public fecha?: Moment,
    public idUser?: IUser
  ) {
    this.acutalizado = this.acutalizado || false;
  }
}
