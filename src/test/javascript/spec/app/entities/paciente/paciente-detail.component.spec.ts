import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { Surapp1TestModule } from '../../../test.module';
import { PacienteDetailComponent } from 'app/entities/paciente/paciente-detail.component';
import { Paciente } from 'app/shared/model/paciente.model';

describe('Component Tests', () => {
  describe('Paciente Management Detail Component', () => {
    let comp: PacienteDetailComponent;
    let fixture: ComponentFixture<PacienteDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ paciente: new Paciente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [PacienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PacienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PacienteDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load paciente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paciente).toEqual(jasmine.objectContaining({ id: 123 }));
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
