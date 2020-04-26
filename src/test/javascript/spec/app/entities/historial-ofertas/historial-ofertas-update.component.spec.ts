import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { HistorialOfertasUpdateComponent } from 'app/entities/historial-ofertas/historial-ofertas-update.component';
import { HistorialOfertasService } from 'app/entities/historial-ofertas/historial-ofertas.service';
import { HistorialOfertas } from 'app/shared/model/historial-ofertas.model';

describe('Component Tests', () => {
  describe('HistorialOfertas Management Update Component', () => {
    let comp: HistorialOfertasUpdateComponent;
    let fixture: ComponentFixture<HistorialOfertasUpdateComponent>;
    let service: HistorialOfertasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [HistorialOfertasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HistorialOfertasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistorialOfertasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistorialOfertasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HistorialOfertas(123);
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
        const entity = new HistorialOfertas();
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
