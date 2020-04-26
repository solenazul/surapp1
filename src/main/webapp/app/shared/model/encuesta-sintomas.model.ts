import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IEncuestaSintomas {
  id?: number;
  fiebre?: boolean;
  dolorGarganta?: boolean;
  congestionNasal?: boolean;
  tos?: boolean;
  dificultadRespirar?: boolean;
  fatiga?: boolean;
  escalofrio?: boolean;
  dolorMuscular?: boolean;
  ninguno?: boolean;
  fecha?: Moment;
  user?: IUser;
}

export class EncuestaSintomas implements IEncuestaSintomas {
  constructor(
    public id?: number,
    public fiebre?: boolean,
    public dolorGarganta?: boolean,
    public congestionNasal?: boolean,
    public tos?: boolean,
    public dificultadRespirar?: boolean,
    public fatiga?: boolean,
    public escalofrio?: boolean,
    public dolorMuscular?: boolean,
    public ninguno?: boolean,
    public fecha?: Moment,
    public user?: IUser
  ) {
    this.fiebre = this.fiebre || false;
    this.dolorGarganta = this.dolorGarganta || false;
    this.congestionNasal = this.congestionNasal || false;
    this.tos = this.tos || false;
    this.dificultadRespirar = this.dificultadRespirar || false;
    this.fatiga = this.fatiga || false;
    this.escalofrio = this.escalofrio || false;
    this.dolorMuscular = this.dolorMuscular || false;
    this.ninguno = this.ninguno || false;
  }
}
