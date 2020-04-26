import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { ComentarioBlogDetailComponent } from 'app/entities/comentario-blog/comentario-blog-detail.component';
import { ComentarioBlog } from 'app/shared/model/comentario-blog.model';

describe('Component Tests', () => {
  describe('ComentarioBlog Management Detail Component', () => {
    let comp: ComentarioBlogDetailComponent;
    let fixture: ComponentFixture<ComentarioBlogDetailComponent>;
    const route = ({ data: of({ comentarioBlog: new ComentarioBlog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [ComentarioBlogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComentarioBlogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComentarioBlogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load comentarioBlog on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comentarioBlog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
