import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { AlarmaDetailComponent } from 'app/entities/alarma/alarma-detail.component';
import { Alarma } from 'app/shared/model/alarma.model';

describe('Component Tests', () => {
  describe('Alarma Management Detail Component', () => {
    let comp: AlarmaDetailComponent;
    let fixture: ComponentFixture<AlarmaDetailComponent>;
    const route = ({ data: of({ alarma: new Alarma(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [AlarmaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AlarmaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlarmaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alarma on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alarma).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
