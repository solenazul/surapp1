import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { TablerosUpdateComponent } from 'app/entities/tableros/tableros-update.component';
import { TablerosService } from 'app/entities/tableros/tableros.service';
import { Tableros } from 'app/shared/model/tableros.model';

describe('Component Tests', () => {
  describe('Tableros Management Update Component', () => {
    let comp: TablerosUpdateComponent;
    let fixture: ComponentFixture<TablerosUpdateComponent>;
    let service: TablerosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [TablerosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TablerosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TablerosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TablerosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tableros(123);
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
        const entity = new Tableros();
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
