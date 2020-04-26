import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { ComentariosServiciosUpdateComponent } from 'app/entities/comentarios-servicios/comentarios-servicios-update.component';
import { ComentariosServiciosService } from 'app/entities/comentarios-servicios/comentarios-servicios.service';
import { ComentariosServicios } from 'app/shared/model/comentarios-servicios.model';

describe('Component Tests', () => {
  describe('ComentariosServicios Management Update Component', () => {
    let comp: ComentariosServiciosUpdateComponent;
    let fixture: ComponentFixture<ComentariosServiciosUpdateComponent>;
    let service: ComentariosServiciosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [ComentariosServiciosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComentariosServiciosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComentariosServiciosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComentariosServiciosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ComentariosServicios(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ComentariosServicios();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
