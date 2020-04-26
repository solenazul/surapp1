import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AlarmaService } from 'app/entities/alarma/alarma.service';
import { IAlarma, Alarma } from 'app/shared/model/alarma.model';

describe('Service Tests', () => {
  describe('Alarma Service', () => {
    let injector: TestBed;
    let service: AlarmaService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlarma;
    let expectedResult: IAlarma | IAlarma[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlarmaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Alarma(0, currentDate, 'AAAAAAA', 'AAAAAAA');
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

      it('should create a Alarma', () => {
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

        service.create(new Alarma()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Alarma', () => {
        const returnedFromService = Object.assign(
          {
            timeInstant: currentDate.format(DATE_TIME_FORMAT),
            descripcion: 'BBBBBB',
            procedimiento: 'BBBBBB'
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

      it('should return a list of Alarma', () => {
        const returnedFromService = Object.assign(
          {
            timeInstant: currentDate.format(DATE_TIME_FORMAT),
            descripcion: 'BBBBBB',
            procedimiento: 'BBBBBB'
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

      it('should delete a Alarma', () => {
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
