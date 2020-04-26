import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { EncuestaEntornoUpdateComponent } from 'app/entities/encuesta-entorno/encuesta-entorno-update.component';
import { EncuestaEntornoService } from 'app/entities/encuesta-entorno/encuesta-entorno.service';
import { EncuestaEntorno } from 'app/shared/model/encuesta-entorno.model';

describe('Component Tests', () => {
  describe('EncuestaEntorno Management Update Component', () => {
    let comp: EncuestaEntornoUpdateComponent;
    let fixture: ComponentFixture<EncuestaEntornoUpdateComponent>;
    let service: EncuestaEntornoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [EncuestaEntornoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EncuestaEntornoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EncuestaEntornoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EncuestaEntornoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EncuestaEntorno(123);
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
        const entity = new EncuestaEntorno();
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
