package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class ComentariosProductoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentariosProducto.class);
        ComentariosProducto comentariosProducto1 = new ComentariosProducto();
        comentariosProducto1.setId(1L);
        ComentariosProducto comentariosProducto2 = new ComentariosProducto();
        comentariosProducto2.setId(comentariosProducto1.getId());
        assertThat(comentariosProducto1).isEqualTo(comentariosProducto2);
        comentariosProducto2.setId(2L);
        assertThat(comentariosProducto1).isNotEqualTo(comentariosProducto2);
        comentariosProducto1.setId(null);
        assertThat(comentariosProducto1).isNotEqualTo(comentariosProducto2);
    }
}
