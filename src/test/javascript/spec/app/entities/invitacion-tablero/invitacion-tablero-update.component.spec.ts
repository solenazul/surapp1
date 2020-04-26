import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { InvitacionTableroUpdateComponent } from 'app/entities/invitacion-tablero/invitacion-tablero-update.component';
import { InvitacionTableroService } from 'app/entities/invitacion-tablero/invitacion-tablero.service';
import { InvitacionTablero } from 'app/shared/model/invitacion-tablero.model';

describe('Component Tests', () => {
  describe('InvitacionTablero Management Update Component', () => {
    let comp: InvitacionTableroUpdateComponent;
    let fixture: ComponentFixture<InvitacionTableroUpdateComponent>;
    let service: InvitacionTableroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [InvitacionTableroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InvitacionTableroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvitacionTableroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitacionTableroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvitacionTablero(123);
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
        const entity = new InvitacionTablero();
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
