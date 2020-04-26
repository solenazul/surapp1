import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { ContenidoTableroUpdateComponent } from 'app/entities/contenido-tablero/contenido-tablero-update.component';
import { ContenidoTableroService } from 'app/entities/contenido-tablero/contenido-tablero.service';
import { ContenidoTablero } from 'app/shared/model/contenido-tablero.model';

describe('Component Tests', () => {
  describe('ContenidoTablero Management Update Component', () => {
    let comp: ContenidoTableroUpdateComponent;
    let fixture: ComponentFixture<ContenidoTableroUpdateComponent>;
    let service: ContenidoTableroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [ContenidoTableroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContenidoTableroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContenidoTableroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContenidoTableroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContenidoTablero(123);
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
        const entity = new ContenidoTablero();
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
