import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { FavoritosProductosUpdateComponent } from 'app/entities/favoritos-productos/favoritos-productos-update.component';
import { FavoritosProductosService } from 'app/entities/favoritos-productos/favoritos-productos.service';
import { FavoritosProductos } from 'app/shared/model/favoritos-productos.model';

describe('Component Tests', () => {
  describe('FavoritosProductos Management Update Component', () => {
    let comp: FavoritosProductosUpdateComponent;
    let fixture: ComponentFixture<FavoritosProductosUpdateComponent>;
    let service: FavoritosProductosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [FavoritosProductosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FavoritosProductosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FavoritosProductosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FavoritosProductosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FavoritosProductos(123);
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
        const entity = new FavoritosProductos();
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
