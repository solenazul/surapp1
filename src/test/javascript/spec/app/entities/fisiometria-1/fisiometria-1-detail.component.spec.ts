import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { Fisiometria1DetailComponent } from 'app/entities/fisiometria-1/fisiometria-1-detail.component';
import { Fisiometria1 } from 'app/shared/model/fisiometria-1.model';

describe('Component Tests', () => {
  describe('Fisiometria1 Management Detail Component', () => {
    let comp: Fisiometria1DetailComponent;
    let fixture: ComponentFixture<Fisiometria1DetailComponent>;
    const route = ({ data: of({ fisiometria1: new Fisiometria1(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [Fisiometria1DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Fisiometria1DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Fisiometria1DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fisiometria1 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fisiometria1).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
