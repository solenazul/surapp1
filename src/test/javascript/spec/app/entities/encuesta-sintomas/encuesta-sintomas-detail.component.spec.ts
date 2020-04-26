import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { EncuestaSintomasDetailComponent } from 'app/entities/encuesta-sintomas/encuesta-sintomas-detail.component';
import { EncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';

describe('Component Tests', () => {
  describe('EncuestaSintomas Management Detail Component', () => {
    let comp: EncuestaSintomasDetailComponent;
    let fixture: ComponentFixture<EncuestaSintomasDetailComponent>;
    const route = ({ data: of({ encuestaSintomas: new EncuestaSintomas(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [EncuestaSintomasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EncuestaSintomasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EncuestaSintomasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load encuestaSintomas on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.encuestaSintomas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
