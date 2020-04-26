import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EncuestaEntornoService } from 'app/entities/encuesta-entorno/encuesta-entorno.service';
import { IEncuestaEntorno, EncuestaEntorno } from 'app/shared/model/encuesta-entorno.model';

describe('Service Tests', () => {
  describe('EncuestaEntorno Service', () => {
    let injector: TestBed;
    let service: EncuestaEntornoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEncuestaEntorno;
    let expectedResult: IEncuestaEntorno | IEncuestaEntorno[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EncuestaEntornoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EncuestaEntorno(0, false, false, false, false, false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EncuestaEntorno', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );

        service.create(new EncuestaEntorno()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EncuestaEntorno', () => {
        const returnedFromService = Object.assign(
          {
            contactoSintomas: true,
            viajeInternacional: true,
            viajeNacional: true,
            trabajadorSalud: true,
            ninguna: true,
            fecha: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EncuestaEntorno', () => {
        const returnedFromService = Object.assign(
          {
            contactoSintomas: true,
            viajeInternacional: true,
            viajeNacional: true,
            trabajadorSalud: true,
            ninguna: true,
            fecha: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EncuestaEntorno', () => {
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
