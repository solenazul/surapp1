import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { FavoritosServiciosDetailComponent } from 'app/entities/favoritos-servicios/favoritos-servicios-detail.component';
import { FavoritosServicios } from 'app/shared/model/favoritos-servicios.model';

describe('Component Tests', () => {
  describe('FavoritosServicios Management Detail Component', () => {
    let comp: FavoritosServiciosDetailComponent;
    let fixture: ComponentFixture<FavoritosServiciosDetailComponent>;
    const route = ({ data: of({ favoritosServicios: new FavoritosServicios(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [FavoritosServiciosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FavoritosServiciosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FavoritosServiciosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load favoritosServicios on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.favoritosServicios).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
