package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class Modelo3DTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modelo3D.class);
        Modelo3D modelo3D1 = new Modelo3D();
        modelo3D1.setId(1L);
        Modelo3D modelo3D2 = new Modelo3D();
        modelo3D2.setId(modelo3D1.getId());
        assertThat(modelo3D1).isEqualTo(modelo3D2);
        modelo3D2.setId(2L);
        assertThat(modelo3D1).isNotEqualTo(modelo3D2);
        modelo3D1.setId(null);
        assertThat(modelo3D1).isNotEqualTo(modelo3D2);
    }
}
