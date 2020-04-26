import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { IPSUpdateComponent } from 'app/entities/ips/ips-update.component';
import { IPSService } from 'app/entities/ips/ips.service';
import { IPS } from 'app/shared/model/ips.model';

describe('Component Tests', () => {
  describe('IPS Management Update Component', () => {
    let comp: IPSUpdateComponent;
    let fixture: ComponentFixture<IPSUpdateComponent>;
    let service: IPSService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [IPSUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IPSUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IPSUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IPSService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IPS(123);
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
        const entity = new IPS();
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
