import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { FavoritosServiciosUpdateComponent } from 'app/entities/favoritos-servicios/favoritos-servicios-update.component';
import { FavoritosServiciosService } from 'app/entities/favoritos-servicios/favoritos-servicios.service';
import { FavoritosServicios } from 'app/shared/model/favoritos-servicios.model';

describe('Component Tests', () => {
  describe('FavoritosServicios Management Update Component', () => {
    let comp: FavoritosServiciosUpdateComponent;
    let fixture: ComponentFixture<FavoritosServiciosUpdateComponent>;
    let service: FavoritosServiciosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [FavoritosServiciosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FavoritosServiciosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FavoritosServiciosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FavoritosServiciosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FavoritosServicios(123);
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
        const entity = new FavoritosServicios();
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
