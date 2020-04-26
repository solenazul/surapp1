export interface IProductos {
  id?: number;
  nombre?: string;
  descripcion?: string;
  imagenContentType?: string;
  imagen?: any;
  inventario?: number;
  tipo?: number;
  impuesto?: number;
  valor?: number;
  unidad?: number;
  estado?: number;
  tiempoEntrega?: number;
  dispinibilidad?: boolean;
  nuevo?: boolean;
  descuento?: number;
  remate?: boolean;
  tags?: string;
  puntuacion?: number;
  vistos?: number;
  oferta?: number;
  tiempoOferta?: number;
}

export class Productos implements IProductos {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public inventario?: number,
    public tipo?: number,
    public impuesto?: number,
    public valor?: number,
    public unidad?: number,
    public estado?: number,
    public tiempoEntrega?: number,
    public dispinibilidad?: boolean,
    public nuevo?: boolean,
    public descuento?: number,
    public remate?: boolean,
    public tags?: string,
    public puntuacion?: number,
    public vistos?: number,
    public oferta?: number,
    public tiempoOferta?: number
  ) {
    this.dispinibilidad = this.dispinibilidad || false;
    this.nuevo = this.nuevo || false;
    this.remate = this.remate || false;
  }
}
