package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class FavoritosProductosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoritosProductos.class);
        FavoritosProductos favoritosProductos1 = new FavoritosProductos();
        favoritosProductos1.setId(1L);
        FavoritosProductos favoritosProductos2 = new FavoritosProductos();
        favoritosProductos2.setId(favoritosProductos1.getId());
        assertThat(favoritosProductos1).isEqualTo(favoritosProductos2);
        favoritosProductos2.setId(2L);
        assertThat(favoritosProductos1).isNotEqualTo(favoritosProductos2);
        favoritosProductos1.setId(null);
        assertThat(favoritosProductos1).isNotEqualTo(favoritosProductos2);
    }
}
