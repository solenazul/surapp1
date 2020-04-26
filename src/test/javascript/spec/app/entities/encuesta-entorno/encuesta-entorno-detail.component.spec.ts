import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { EncuestaEntornoDetailComponent } from 'app/entities/encuesta-entorno/encuesta-entorno-detail.component';
import { EncuestaEntorno } from 'app/shared/model/encuesta-entorno.model';

describe('Component Tests', () => {
  describe('EncuestaEntorno Management Detail Component', () => {
    let comp: EncuestaEntornoDetailComponent;
    let fixture: ComponentFixture<EncuestaEntornoDetailComponent>;
    const route = ({ data: of({ encuestaEntorno: new EncuestaEntorno(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [EncuestaEntornoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EncuestaEntornoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EncuestaEntornoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load encuestaEntorno on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.encuestaEntorno).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
