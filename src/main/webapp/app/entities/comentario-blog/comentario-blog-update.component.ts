import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IComentarioBlog, ComentarioBlog } from 'app/shared/model/comentario-blog.model';
import { ComentarioBlogService } from './comentario-blog.service';
import { IBlog } from 'app/shared/model/blog.model';
import { BlogService } from 'app/entities/blog/blog.service';

@Component({
  selector: 'jhi-comentario-blog-update',
  templateUrl: './comentario-blog-update.component.html'
})
export class ComentarioBlogUpdateComponent implements OnInit {
  isSaving = false;
  blogs: IBlog[] = [];

  editForm = this.fb.group({
    id: [],
    comentario: [],
    fecha: [],
    estado: [],
    blogId: []
  });

  constructor(
    protected comentarioBlogService: ComentarioBlogService,
    protected blogService: BlogService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentarioBlog }) => {
      if (!comentarioBlog.id) {
        const today = moment().startOf('day');
        comentarioBlog.fecha = today;
      }

      this.updateForm(comentarioBlog);

      this.blogService.query().subscribe((res: HttpResponse<IBlog[]>) => (this.blogs = res.body || []));
    });
  }

  updateForm(comentarioBlog: IComentarioBlog): void {
    this.editForm.patchValue({
      id: comentarioBlog.id,
      comentario: comentarioBlog.comentario,
      fecha: comentarioBlog.fecha ? comentarioBlog.fecha.format(DATE_TIME_FORMAT) : null,
      estado: comentarioBlog.estado,
      blogId: comentarioBlog.blogId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comentarioBlog = this.createFromForm();
    if (comentarioBlog.id !== undefined) {
      this.subscribeToSaveResponse(this.comentarioBlogService.update(comentarioBlog));
    } else {
      this.subscribeToSaveResponse(this.comentarioBlogService.create(comentarioBlog));
    }
  }

  private createFromForm(): IComentarioBlog {
    return {
      ...new ComentarioBlog(),
      id: this.editForm.get(['id'])!.value,
      comentario: this.editForm.get(['comentario'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      estado: this.editForm.get(['estado'])!.value,
      blogId: this.editForm.get(['blogId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComentarioBlog>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBlog): any {
    return item.id;
  }
}
