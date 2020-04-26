import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { CategoriasUpdateComponent } from 'app/entities/categorias/categorias-update.component';
import { CategoriasService } from 'app/entities/categorias/categorias.service';
import { Categorias } from 'app/shared/model/categorias.model';

describe('Component Tests', () => {
  describe('Categorias Management Update Component', () => {
    let comp: CategoriasUpdateComponent;
    let fixture: ComponentFixture<CategoriasUpdateComponent>;
    let service: CategoriasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [CategoriasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoriasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Categorias(123);
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
        const entity = new Categorias();
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
