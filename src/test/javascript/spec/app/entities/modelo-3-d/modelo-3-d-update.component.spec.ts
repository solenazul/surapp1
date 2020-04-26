import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { Modelo3DUpdateComponent } from 'app/entities/modelo-3-d/modelo-3-d-update.component';
import { Modelo3DService } from 'app/entities/modelo-3-d/modelo-3-d.service';
import { Modelo3D } from 'app/shared/model/modelo-3-d.model';

describe('Component Tests', () => {
  describe('Modelo3D Management Update Component', () => {
    let comp: Modelo3DUpdateComponent;
    let fixture: ComponentFixture<Modelo3DUpdateComponent>;
    let service: Modelo3DService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [Modelo3DUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Modelo3DUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Modelo3DUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Modelo3DService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Modelo3D(123);
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
        const entity = new Modelo3D();
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
