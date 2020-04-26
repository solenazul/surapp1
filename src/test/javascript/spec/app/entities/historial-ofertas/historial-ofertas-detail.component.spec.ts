import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { HistorialOfertasDetailComponent } from 'app/entities/historial-ofertas/historial-ofertas-detail.component';
import { HistorialOfertas } from 'app/shared/model/historial-ofertas.model';

describe('Component Tests', () => {
  describe('HistorialOfertas Management Detail Component', () => {
    let comp: HistorialOfertasDetailComponent;
    let fixture: ComponentFixture<HistorialOfertasDetailComponent>;
    const route = ({ data: of({ historialOfertas: new HistorialOfertas(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [HistorialOfertasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HistorialOfertasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistorialOfertasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load historialOfertas on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.historialOfertas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
