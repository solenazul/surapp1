package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class CategoriaProductoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaProducto.class);
        CategoriaProducto categoriaProducto1 = new CategoriaProducto();
        categoriaProducto1.setId(1L);
        CategoriaProducto categoriaProducto2 = new CategoriaProducto();
        categoriaProducto2.setId(categoriaProducto1.getId());
        assertThat(categoriaProducto1).isEqualTo(categoriaProducto2);
        categoriaProducto2.setId(2L);
        assertThat(categoriaProducto1).isNotEqualTo(categoriaProducto2);
        categoriaProducto1.setId(null);
        assertThat(categoriaProducto1).isNotEqualTo(categoriaProducto2);
    }
}
