import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Fisiometria1Service } from 'app/entities/fisiometria-1/fisiometria-1.service';
import { IFisiometria1, Fisiometria1 } from 'app/shared/model/fisiometria-1.model';

describe('Service Tests', () => {
  describe('Fisiometria1 Service', () => {
    let injector: TestBed;
    let service: Fisiometria1Service;
    let httpMock: HttpTestingController;
    let elemDefault: IFisiometria1;
    let expectedResult: IFisiometria1 | IFisiometria1[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(Fisiometria1Service);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Fisiometria1(0, 0, 0, 0, 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            timeInstant: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Fisiometria1', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            timeInstant: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timeInstant: currentDate
          },
          returnedFromService
        );

        service.create(new Fisiometria1()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Fisiometria1', () => {
        const returnedFromService = Object.assign(
          {
            ritmoCardiaco: 1,
            ritmoRespiratorio: 1,
            oximetria: 1,
            presionArterialSistolica: 1,
            presionArterialDiastolica: 1,
            temperatura: 1,
            timeInstant: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timeInstant: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Fisiometria1', () => {
        const returnedFromService = Object.assign(
          {
            ritmoCardiaco: 1,
            ritmoRespiratorio: 1,
            oximetria: 1,
            presionArterialSistolica: 1,
            presionArterialDiastolica: 1,
            temperatura: 1,
            timeInstant: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timeInstant: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Fisiometria1', () => {
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
