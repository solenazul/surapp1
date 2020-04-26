import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { Surapp1TestModule } from '../../../test.module';
import { DatosUsuarioDetailComponent } from 'app/entities/datos-usuario/datos-usuario-detail.component';
import { DatosUsuario } from 'app/shared/model/datos-usuario.model';

describe('Component Tests', () => {
  describe('DatosUsuario Management Detail Component', () => {
    let comp: DatosUsuarioDetailComponent;
    let fixture: ComponentFixture<DatosUsuarioDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ datosUsuario: new DatosUsuario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [DatosUsuarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DatosUsuarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatosUsuarioDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load datosUsuario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datosUsuario).toEqual(jasmine.objectContaining({ id: 123 }));
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
