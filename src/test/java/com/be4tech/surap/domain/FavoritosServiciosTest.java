package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class FavoritosServiciosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoritosServicios.class);
        FavoritosServicios favoritosServicios1 = new FavoritosServicios();
        favoritosServicios1.setId(1L);
        FavoritosServicios favoritosServicios2 = new FavoritosServicios();
        favoritosServicios2.setId(favoritosServicios1.getId());
        assertThat(favoritosServicios1).isEqualTo(favoritosServicios2);
        favoritosServicios2.setId(2L);
        assertThat(favoritosServicios1).isNotEqualTo(favoritosServicios2);
        favoritosServicios1.setId(null);
        assertThat(favoritosServicios1).isNotEqualTo(favoritosServicios2);
    }
}
