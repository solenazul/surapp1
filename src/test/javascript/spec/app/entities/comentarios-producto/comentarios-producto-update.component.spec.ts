import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { ComentariosProductoUpdateComponent } from 'app/entities/comentarios-producto/comentarios-producto-update.component';
import { ComentariosProductoService } from 'app/entities/comentarios-producto/comentarios-producto.service';
import { ComentariosProducto } from 'app/shared/model/comentarios-producto.model';

describe('Component Tests', () => {
  describe('ComentariosProducto Management Update Component', () => {
    let comp: ComentariosProductoUpdateComponent;
    let fixture: ComponentFixture<ComentariosProductoUpdateComponent>;
    let service: ComentariosProductoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [ComentariosProductoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComentariosProductoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComentariosProductoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComentariosProductoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ComentariosProducto(123);
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
        const entity = new ComentariosProducto();
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
