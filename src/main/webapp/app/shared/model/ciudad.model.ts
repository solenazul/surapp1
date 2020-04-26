import { IPais } from 'app/shared/model/pais.model';

export interface ICiudad {
  id?: number;
  nombreCiudad?: string;
  pais?: IPais;
}

export class Ciudad implements ICiudad {
  constructor(public id?: number, public nombreCiudad?: string, public pais?: IPais) {}
}
