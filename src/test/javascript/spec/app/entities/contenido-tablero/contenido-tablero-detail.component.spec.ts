import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { ContenidoTableroDetailComponent } from 'app/entities/contenido-tablero/contenido-tablero-detail.component';
import { ContenidoTablero } from 'app/shared/model/contenido-tablero.model';

describe('Component Tests', () => {
  describe('ContenidoTablero Management Detail Component', () => {
    let comp: ContenidoTableroDetailComponent;
    let fixture: ComponentFixture<ContenidoTableroDetailComponent>;
    const route = ({ data: of({ contenidoTablero: new ContenidoTablero(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [ContenidoTableroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContenidoTableroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContenidoTableroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contenidoTablero on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contenidoTablero).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
