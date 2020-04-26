export interface IServicios {
  id?: number;
  nombre?: string;
  descripcion?: string;
  imagenContentType?: string;
  imagen?: any;
  videosContentType?: string;
  videos?: any;
  documentoContentType?: string;
  documento?: any;
  proveedor?: string;
  impuesto?: number;
  valor?: number;
  unidad?: number;
  dispinibilidad?: boolean;
  descuento?: number;
  remate?: boolean;
  tags?: string;
  puntuacion?: number;
  vistos?: number;
  oferta?: number;
  tiempoOferta?: number;
}

export class Servicios implements IServicios {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public videosContentType?: string,
    public videos?: any,
    public documentoContentType?: string,
    public documento?: any,
    public proveedor?: string,
    public impuesto?: number,
    public valor?: number,
    public unidad?: number,
    public dispinibilidad?: boolean,
    public descuento?: number,
    public remate?: boolean,
    public tags?: string,
    public puntuacion?: number,
    public vistos?: number,
    public oferta?: number,
    public tiempoOferta?: number
  ) {
    this.dispinibilidad = this.dispinibilidad || false;
    this.remate = this.remate || false;
  }
}
