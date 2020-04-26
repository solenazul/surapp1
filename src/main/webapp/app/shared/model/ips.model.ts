export interface IIPS {
  id?: number;
  nombre?: string;
  nit?: string;
  direccion?: string;
  telefono?: string;
  correoElectronico?: string;
}

export class IPS implements IIPS {
  constructor(
    public id?: number,
    public nombre?: string,
    public nit?: string,
    public direccion?: string,
    public telefono?: string,
    public correoElectronico?: string
  ) {}
}
