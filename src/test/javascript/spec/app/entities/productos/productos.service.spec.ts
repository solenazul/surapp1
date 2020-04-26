import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductosService } from 'app/entities/productos/productos.service';
import { IProductos, Productos } from 'app/shared/model/productos.model';

describe('Service Tests', () => {
  describe('Productos Service', () => {
    let injector: TestBed;
    let service: ProductosService;
    let httpMock: HttpTestingController;
    let elemDefault: IProductos;
    let expectedResult: IProductos | IProductos[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProductosService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Productos(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        false,
        false,
        0,
        false,
        'AAAAAAA',
        0,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Productos', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Productos()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Productos', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            imagen: 'BBBBBB',
            inventario: 1,
            tipo: 1,
            impuesto: 1,
            valor: 1,
            unidad: 1,
            estado: 1,
            tiempoEntrega: 1,
            dispinibilidad: true,
            nuevo: true,
            descuento: 1,
            remate: true,
            tags: 'BBBBBB',
            puntuacion: 1,
            vistos: 1,
            oferta: 1,
            tiempoOferta: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Productos', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            imagen: 'BBBBBB',
            inventario: 1,
            tipo: 1,
            impuesto: 1,
            valor: 1,
            unidad: 1,
            estado: 1,
            tiempoEntrega: 1,
            dispinibilidad: true,
            nuevo: true,
            descuento: 1,
            remate: true,
            tags: 'BBBBBB',
            puntuacion: 1,
            vistos: 1,
            oferta: 1,
            tiempoOferta: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Productos', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
