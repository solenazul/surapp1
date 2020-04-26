import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { HistorialOfertasService } from 'app/entities/historial-ofertas/historial-ofertas.service';
import { IHistorialOfertas, HistorialOfertas } from 'app/shared/model/historial-ofertas.model';

describe('Service Tests', () => {
  describe('HistorialOfertas Service', () => {
    let injector: TestBed;
    let service: HistorialOfertasService;
    let httpMock: HttpTestingController;
    let elemDefault: IHistorialOfertas;
    let expectedResult: IHistorialOfertas | IHistorialOfertas[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HistorialOfertasService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new HistorialOfertas(0, 0, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaInicial: currentDate.format(DATE_FORMAT),
            fechaFinal: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a HistorialOfertas', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaInicial: currentDate.format(DATE_FORMAT),
            fechaFinal: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicial: currentDate,
            fechaFinal: currentDate
          },
          returnedFromService
        );

        service.create(new HistorialOfertas()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HistorialOfertas', () => {
        const returnedFromService = Object.assign(
          {
            valorProducto: 1,
            valorOferta: 1,
            fechaInicial: currentDate.format(DATE_FORMAT),
            fechaFinal: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicial: currentDate,
            fechaFinal: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HistorialOfertas', () => {
        const returnedFromService = Object.assign(
          {
            valorProducto: 1,
            valorOferta: 1,
            fechaInicial: currentDate.format(DATE_FORMAT),
            fechaFinal: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicial: currentDate,
            fechaFinal: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a HistorialOfertas', () => {
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
