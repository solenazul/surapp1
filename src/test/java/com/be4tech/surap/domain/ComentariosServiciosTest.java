package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class ComentariosServiciosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentariosServicios.class);
        ComentariosServicios comentariosServicios1 = new ComentariosServicios();
        comentariosServicios1.setId(1L);
        ComentariosServicios comentariosServicios2 = new ComentariosServicios();
        comentariosServicios2.setId(comentariosServicios1.getId());
        assertThat(comentariosServicios1).isEqualTo(comentariosServicios2);
        comentariosServicios2.setId(2L);
        assertThat(comentariosServicios1).isNotEqualTo(comentariosServicios2);
        comentariosServicios1.setId(null);
        assertThat(comentariosServicios1).isNotEqualTo(comentariosServicios2);
    }
}
