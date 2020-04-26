import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { CategoriaProductoDetailComponent } from 'app/entities/categoria-producto/categoria-producto-detail.component';
import { CategoriaProducto } from 'app/shared/model/categoria-producto.model';

describe('Component Tests', () => {
  describe('CategoriaProducto Management Detail Component', () => {
    let comp: CategoriaProductoDetailComponent;
    let fixture: ComponentFixture<CategoriaProductoDetailComponent>;
    const route = ({ data: of({ categoriaProducto: new CategoriaProducto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [CategoriaProductoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaProductoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaProductoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoriaProducto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriaProducto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
