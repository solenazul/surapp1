import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { CategoriaProductoUpdateComponent } from 'app/entities/categoria-producto/categoria-producto-update.component';
import { CategoriaProductoService } from 'app/entities/categoria-producto/categoria-producto.service';
import { CategoriaProducto } from 'app/shared/model/categoria-producto.model';

describe('Component Tests', () => {
  describe('CategoriaProducto Management Update Component', () => {
    let comp: CategoriaProductoUpdateComponent;
    let fixture: ComponentFixture<CategoriaProductoUpdateComponent>;
    let service: CategoriaProductoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [CategoriaProductoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoriaProductoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaProductoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaProductoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoriaProducto(123);
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
        const entity = new CategoriaProducto();
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
