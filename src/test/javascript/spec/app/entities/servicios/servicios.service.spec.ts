import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ServiciosService } from 'app/entities/servicios/servicios.service';
import { IServicios, Servicios } from 'app/shared/model/servicios.model';

describe('Service Tests', () => {
  describe('Servicios Service', () => {
    let injector: TestBed;
    let service: ServiciosService;
    let httpMock: HttpTestingController;
    let elemDefault: IServicios;
    let expectedResult: IServicios | IServicios[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ServiciosService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Servicios(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
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

      it('should create a Servicios', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Servicios()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Servicios', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            imagen: 'BBBBBB',
            videos: 'BBBBBB',
            documento: 'BBBBBB',
            proveedor: 'BBBBBB',
            impuesto: 1,
            valor: 1,
            unidad: 1,
            dispinibilidad: true,
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

      it('should return a list of Servicios', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            imagen: 'BBBBBB',
            videos: 'BBBBBB',
            documento: 'BBBBBB',
            proveedor: 'BBBBBB',
            impuesto: 1,
            valor: 1,
            unidad: 1,
            dispinibilidad: true,
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

      it('should delete a Servicios', () => {
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
