import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IEncuestaEntorno {
  id?: number;
  contactoSintomas?: boolean;
  viajeInternacional?: boolean;
  viajeNacional?: boolean;
  trabajadorSalud?: boolean;
  ninguna?: boolean;
  fecha?: Moment;
  user?: IUser;
}

export class EncuestaEntorno implements IEncuestaEntorno {
  constructor(
    public id?: number,
    public contactoSintomas?: boolean,
    public viajeInternacional?: boolean,
    public viajeNacional?: boolean,
    public trabajadorSalud?: boolean,
    public ninguna?: boolean,
    public fecha?: Moment,
    public user?: IUser
  ) {
    this.contactoSintomas = this.contactoSintomas || false;
    this.viajeInternacional = this.viajeInternacional || false;
    this.viajeNacional = this.viajeNacional || false;
    this.trabajadorSalud = this.trabajadorSalud || false;
    this.ninguna = this.ninguna || false;
  }
}
