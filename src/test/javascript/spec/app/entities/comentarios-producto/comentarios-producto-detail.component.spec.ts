import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { ComentariosProductoDetailComponent } from 'app/entities/comentarios-producto/comentarios-producto-detail.component';
import { ComentariosProducto } from 'app/shared/model/comentarios-producto.model';

describe('Component Tests', () => {
  describe('ComentariosProducto Management Detail Component', () => {
    let comp: ComentariosProductoDetailComponent;
    let fixture: ComponentFixture<ComentariosProductoDetailComponent>;
    const route = ({ data: of({ comentariosProducto: new ComentariosProducto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [ComentariosProductoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComentariosProductoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComentariosProductoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load comentariosProducto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comentariosProducto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
