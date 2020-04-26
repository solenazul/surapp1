package com.be4tech.surap.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.be4tech.surap.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.be4tech.surap.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.be4tech.surap.domain.User.class.getName());
            createCache(cm, com.be4tech.surap.domain.Authority.class.getName());
            createCache(cm, com.be4tech.surap.domain.User.class.getName() + ".authorities");
            createCache(cm, com.be4tech.surap.domain.DatosUsuario.class.getName());
            createCache(cm, com.be4tech.surap.domain.Tableros.class.getName());
            createCache(cm, com.be4tech.surap.domain.CategoriaTablero.class.getName());
            createCache(cm, com.be4tech.surap.domain.InvitacionTablero.class.getName());
            createCache(cm, com.be4tech.surap.domain.ContenidoTablero.class.getName());
            createCache(cm, com.be4tech.surap.domain.Categorias.class.getName());
            createCache(cm, com.be4tech.surap.domain.Productos.class.getName());
            createCache(cm, com.be4tech.surap.domain.HistorialOfertas.class.getName());
            createCache(cm, com.be4tech.surap.domain.CategoriaProducto.class.getName());
            createCache(cm, com.be4tech.surap.domain.ComentariosProducto.class.getName());
            createCache(cm, com.be4tech.surap.domain.FavoritosProductos.class.getName());
            createCache(cm, com.be4tech.surap.domain.Servicios.class.getName());
            createCache(cm, com.be4tech.surap.domain.FavoritosServicios.class.getName());
            createCache(cm, com.be4tech.surap.domain.ComentariosServicios.class.getName());
            createCache(cm, com.be4tech.surap.domain.Blog.class.getName());
            createCache(cm, com.be4tech.surap.domain.ComentarioBlog.class.getName());
            createCache(cm, com.be4tech.surap.domain.Pais.class.getName());
            createCache(cm, com.be4tech.surap.domain.Ciudad.class.getName());
            createCache(cm, com.be4tech.surap.domain.Modelo3D.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
