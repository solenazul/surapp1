import { Moment } from 'moment';
import { IIPS } from 'app/shared/model/ips.model';
import { IUser } from 'app/core/user/user.model';

export interface IPaciente {
  id?: number;
  nombre?: string;
  identificacion?: number;
  edad?: number;
  sexo?: string;
  fechaNacimiento?: Moment;
  condicion?: any;
  ips?: IIPS;
  user?: IUser;
}

export class Paciente implements IPaciente {
  constructor(
    public id?: number,
    public nombre?: string,
    public identificacion?: number,
    public edad?: number,
    public sexo?: string,
    public fechaNacimiento?: Moment,
    public condicion?: any,
    public ips?: IIPS,
    public user?: IUser
  ) {}
}
