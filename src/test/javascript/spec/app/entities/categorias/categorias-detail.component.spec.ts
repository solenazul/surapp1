import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { CategoriasDetailComponent } from 'app/entities/categorias/categorias-detail.component';
import { Categorias } from 'app/shared/model/categorias.model';

describe('Component Tests', () => {
  describe('Categorias Management Detail Component', () => {
    let comp: CategoriasDetailComponent;
    let fixture: ComponentFixture<CategoriasDetailComponent>;
    const route = ({ data: of({ categorias: new Categorias(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [CategoriasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorias on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorias).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
