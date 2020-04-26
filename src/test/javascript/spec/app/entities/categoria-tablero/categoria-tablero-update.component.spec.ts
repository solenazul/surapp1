import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { CategoriaTableroUpdateComponent } from 'app/entities/categoria-tablero/categoria-tablero-update.component';
import { CategoriaTableroService } from 'app/entities/categoria-tablero/categoria-tablero.service';
import { CategoriaTablero } from 'app/shared/model/categoria-tablero.model';

describe('Component Tests', () => {
  describe('CategoriaTablero Management Update Component', () => {
    let comp: CategoriaTableroUpdateComponent;
    let fixture: ComponentFixture<CategoriaTableroUpdateComponent>;
    let service: CategoriaTableroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [CategoriaTableroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoriaTableroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriaTableroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriaTableroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoriaTablero(123);
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
        const entity = new CategoriaTablero();
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
