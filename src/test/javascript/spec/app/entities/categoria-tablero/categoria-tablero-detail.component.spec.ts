import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { CategoriaTableroDetailComponent } from 'app/entities/categoria-tablero/categoria-tablero-detail.component';
import { CategoriaTablero } from 'app/shared/model/categoria-tablero.model';

describe('Component Tests', () => {
  describe('CategoriaTablero Management Detail Component', () => {
    let comp: CategoriaTableroDetailComponent;
    let fixture: ComponentFixture<CategoriaTableroDetailComponent>;
    const route = ({ data: of({ categoriaTablero: new CategoriaTablero(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [CategoriaTableroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaTableroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaTableroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoriaTablero on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriaTablero).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
