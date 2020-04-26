import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { Fisiometria1UpdateComponent } from 'app/entities/fisiometria-1/fisiometria-1-update.component';
import { Fisiometria1Service } from 'app/entities/fisiometria-1/fisiometria-1.service';
import { Fisiometria1 } from 'app/shared/model/fisiometria-1.model';

describe('Component Tests', () => {
  describe('Fisiometria1 Management Update Component', () => {
    let comp: Fisiometria1UpdateComponent;
    let fixture: ComponentFixture<Fisiometria1UpdateComponent>;
    let service: Fisiometria1Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [Fisiometria1UpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Fisiometria1UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Fisiometria1UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Fisiometria1Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fisiometria1(123);
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
        const entity = new Fisiometria1();
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
