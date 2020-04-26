import { Moment } from 'moment';
import { IBlog } from 'app/shared/model/blog.model';

export interface IComentarioBlog {
  id?: number;
  comentario?: string;
  fecha?: Moment;
  estado?: string;
  blogId?: IBlog;
}

export class ComentarioBlog implements IComentarioBlog {
  constructor(public id?: number, public comentario?: string, public fecha?: Moment, public estado?: string, public blogId?: IBlog) {}
}
