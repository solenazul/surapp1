package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class CategoriaTableroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaTablero.class);
        CategoriaTablero categoriaTablero1 = new CategoriaTablero();
        categoriaTablero1.setId(1L);
        CategoriaTablero categoriaTablero2 = new CategoriaTablero();
        categoriaTablero2.setId(categoriaTablero1.getId());
        assertThat(categoriaTablero1).isEqualTo(categoriaTablero2);
        categoriaTablero2.setId(2L);
        assertThat(categoriaTablero1).isNotEqualTo(categoriaTablero2);
        categoriaTablero1.setId(null);
        assertThat(categoriaTablero1).isNotEqualTo(categoriaTablero2);
    }
}
