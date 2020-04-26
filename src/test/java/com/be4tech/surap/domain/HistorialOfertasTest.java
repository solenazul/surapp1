package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class HistorialOfertasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistorialOfertas.class);
        HistorialOfertas historialOfertas1 = new HistorialOfertas();
        historialOfertas1.setId(1L);
        HistorialOfertas historialOfertas2 = new HistorialOfertas();
        historialOfertas2.setId(historialOfertas1.getId());
        assertThat(historialOfertas1).isEqualTo(historialOfertas2);
        historialOfertas2.setId(2L);
        assertThat(historialOfertas1).isNotEqualTo(historialOfertas2);
        historialOfertas1.setId(null);
        assertThat(historialOfertas1).isNotEqualTo(historialOfertas2);
    }
}
