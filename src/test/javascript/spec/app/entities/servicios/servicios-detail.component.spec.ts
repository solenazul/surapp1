import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { Surapp1TestModule } from '../../../test.module';
import { ServiciosDetailComponent } from 'app/entities/servicios/servicios-detail.component';
import { Servicios } from 'app/shared/model/servicios.model';

describe('Component Tests', () => {
  describe('Servicios Management Detail Component', () => {
    let comp: ServiciosDetailComponent;
    let fixture: ComponentFixture<ServiciosDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ servicios: new Servicios(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [ServiciosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiciosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiciosDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load servicios on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.servicios).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
