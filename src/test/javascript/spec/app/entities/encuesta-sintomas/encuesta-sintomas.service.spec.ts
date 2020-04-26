import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EncuestaSintomasService } from 'app/entities/encuesta-sintomas/encuesta-sintomas.service';
import { IEncuestaSintomas, EncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';

describe('Service Tests', () => {
  describe('EncuestaSintomas Service', () => {
    let injector: TestBed;
    let service: EncuestaSintomasService;
    let httpMock: HttpTestingController;
    let elemDefault: IEncuestaSintomas;
    let expectedResult: IEncuestaSintomas | IEncuestaSintomas[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EncuestaSintomasService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EncuestaSintomas(0, false, false, false, false, false, false, false, false, false, currentDate);
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

      it('should create a EncuestaSintomas', () => {
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

        service.create(new EncuestaSintomas()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EncuestaSintomas', () => {
        const returnedFromService = Object.assign(
          {
            fiebre: true,
            dolorGarganta: true,
            congestionNasal: true,
            tos: true,
            dificultadRespirar: true,
            fatiga: true,
            escalofrio: true,
            dolorMuscular: true,
            ninguno: true,
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

      it('should return a list of EncuestaSintomas', () => {
        const returnedFromService = Object.assign(
          {
            fiebre: true,
            dolorGarganta: true,
            congestionNasal: true,
            tos: true,
            dificultadRespirar: true,
            fatiga: true,
            escalofrio: true,
            dolorMuscular: true,
            ninguno: true,
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

      it('should delete a EncuestaSintomas', () => {
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
