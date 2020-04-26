package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class ServiciosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servicios.class);
        Servicios servicios1 = new Servicios();
        servicios1.setId(1L);
        Servicios servicios2 = new Servicios();
        servicios2.setId(servicios1.getId());
        assertThat(servicios1).isEqualTo(servicios2);
        servicios2.setId(2L);
        assertThat(servicios1).isNotEqualTo(servicios2);
        servicios1.setId(null);
        assertThat(servicios1).isNotEqualTo(servicios2);
    }
}
