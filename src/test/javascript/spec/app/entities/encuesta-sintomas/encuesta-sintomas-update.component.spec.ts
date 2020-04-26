import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { EncuestaSintomasUpdateComponent } from 'app/entities/encuesta-sintomas/encuesta-sintomas-update.component';
import { EncuestaSintomasService } from 'app/entities/encuesta-sintomas/encuesta-sintomas.service';
import { EncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';

describe('Component Tests', () => {
  describe('EncuestaSintomas Management Update Component', () => {
    let comp: EncuestaSintomasUpdateComponent;
    let fixture: ComponentFixture<EncuestaSintomasUpdateComponent>;
    let service: EncuestaSintomasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [EncuestaSintomasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EncuestaSintomasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EncuestaSintomasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EncuestaSintomasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EncuestaSintomas(123);
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
        const entity = new EncuestaSintomas();
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
