import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComentarioBlog } from 'app/shared/model/comentario-blog.model';

@Component({
  selector: 'jhi-comentario-blog-detail',
  templateUrl: './comentario-blog-detail.component.html'
})
export class ComentarioBlogDetailComponent implements OnInit {
  comentarioBlog: IComentarioBlog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentarioBlog }) => (this.comentarioBlog = comentarioBlog));
  }

  previousState(): void {
    window.history.back();
  }
}
