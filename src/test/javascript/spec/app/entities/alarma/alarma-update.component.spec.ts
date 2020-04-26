import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { AlarmaUpdateComponent } from 'app/entities/alarma/alarma-update.component';
import { AlarmaService } from 'app/entities/alarma/alarma.service';
import { Alarma } from 'app/shared/model/alarma.model';

describe('Component Tests', () => {
  describe('Alarma Management Update Component', () => {
    let comp: AlarmaUpdateComponent;
    let fixture: ComponentFixture<AlarmaUpdateComponent>;
    let service: AlarmaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [AlarmaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlarmaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlarmaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alarma(123);
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
        const entity = new Alarma();
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
