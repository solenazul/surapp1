import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IFisiometria1 {
  id?: number;
  ritmoCardiaco?: number;
  ritmoRespiratorio?: number;
  oximetria?: number;
  presionArterialSistolica?: number;
  presionArterialDiastolica?: number;
  temperatura?: number;
  timeInstant?: Moment;
  user?: IUser;
}

export class Fisiometria1 implements IFisiometria1 {
  constructor(
    public id?: number,
    public ritmoCardiaco?: number,
    public ritmoRespiratorio?: number,
    public oximetria?: number,
    public presionArterialSistolica?: number,
    public presionArterialDiastolica?: number,
    public temperatura?: number,
    public timeInstant?: Moment,
    public user?: IUser
  ) {}
}
