import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'datos-usuario',
        loadChildren: () => import('./datos-usuario/datos-usuario.module').then(m => m.Surapp1DatosUsuarioModule)
      },
      {
        path: 'tableros',
        loadChildren: () => import('./tableros/tableros.module').then(m => m.Surapp1TablerosModule)
      },
      {
        path: 'categoria-tablero',
        loadChildren: () => import('./categoria-tablero/categoria-tablero.module').then(m => m.Surapp1CategoriaTableroModule)
      },
      {
        path: 'invitacion-tablero',
        loadChildren: () => import('./invitacion-tablero/invitacion-tablero.module').then(m => m.Surapp1InvitacionTableroModule)
      },
      {
        path: 'contenido-tablero',
        loadChildren: () => import('./contenido-tablero/contenido-tablero.module').then(m => m.Surapp1ContenidoTableroModule)
      },
      {
        path: 'categorias',
        loadChildren: () => import('./categorias/categorias.module').then(m => m.Surapp1CategoriasModule)
      },
      {
        path: 'productos',
        loadChildren: () => import('./productos/productos.module').then(m => m.Surapp1ProductosModule)
      },
      {
        path: 'historial-ofertas',
        loadChildren: () => import('./historial-ofertas/historial-ofertas.module').then(m => m.Surapp1HistorialOfertasModule)
      },
      {
        path: 'categoria-producto',
        loadChildren: () => import('./categoria-producto/categoria-producto.module').then(m => m.Surapp1CategoriaProductoModule)
      },
      {
        path: 'comentarios-producto',
        loadChildren: () => import('./comentarios-producto/comentarios-producto.module').then(m => m.Surapp1ComentariosProductoModule)
      },
      {
        path: 'favoritos-productos',
        loadChildren: () => import('./favoritos-productos/favoritos-productos.module').then(m => m.Surapp1FavoritosProductosModule)
      },
      {
        path: 'servicios',
        loadChildren: () => import('./servicios/servicios.module').then(m => m.Surapp1ServiciosModule)
      },
      {
        path: 'favoritos-servicios',
        loadChildren: () => import('./favoritos-servicios/favoritos-servicios.module').then(m => m.Surapp1FavoritosServiciosModule)
      },
      {
        path: 'comentarios-servicios',
        loadChildren: () => import('./comentarios-servicios/comentarios-servicios.module').then(m => m.Surapp1ComentariosServiciosModule)
      },
      {
        path: 'blog',
        loadChildren: () => import('./blog/blog.module').then(m => m.Surapp1BlogModule)
      },
      {
        path: 'comentario-blog',
        loadChildren: () => import('./comentario-blog/comentario-blog.module').then(m => m.Surapp1ComentarioBlogModule)
      },
      {
        path: 'pais',
        loadChildren: () => import('./pais/pais.module').then(m => m.Surapp1PaisModule)
      },
      {
        path: 'ciudad',
        loadChildren: () => import('./ciudad/ciudad.module').then(m => m.Surapp1CiudadModule)
      },
      {
        path: 'modelo-3-d',
        loadChildren: () => import('./modelo-3-d/modelo-3-d.module').then(m => m.Surapp1Modelo3DModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Surapp1EntityModule {}
