import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IAlarma {
  id?: number;
  timeInstant?: Moment;
  descripcion?: string;
  procedimiento?: string;
  user?: IUser;
}

export class Alarma implements IAlarma {
  constructor(
    public id?: number,
    public timeInstant?: Moment,
    public descripcion?: string,
    public procedimiento?: string,
    public user?: IUser
  ) {}
}
