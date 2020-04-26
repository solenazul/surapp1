export interface IPais {
  id?: number;
  nombrePais?: string;
}

export class Pais implements IPais {
  constructor(public id?: number, public nombrePais?: string) {}
}
