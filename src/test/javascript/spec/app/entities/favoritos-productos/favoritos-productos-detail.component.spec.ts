import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { FavoritosProductosDetailComponent } from 'app/entities/favoritos-productos/favoritos-productos-detail.component';
import { FavoritosProductos } from 'app/shared/model/favoritos-productos.model';

describe('Component Tests', () => {
  describe('FavoritosProductos Management Detail Component', () => {
    let comp: FavoritosProductosDetailComponent;
    let fixture: ComponentFixture<FavoritosProductosDetailComponent>;
    const route = ({ data: of({ favoritosProductos: new FavoritosProductos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [FavoritosProductosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FavoritosProductosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FavoritosProductosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load favoritosProductos on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.favoritosProductos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
