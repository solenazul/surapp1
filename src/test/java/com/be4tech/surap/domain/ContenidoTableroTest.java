package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class ContenidoTableroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContenidoTablero.class);
        ContenidoTablero contenidoTablero1 = new ContenidoTablero();
        contenidoTablero1.setId(1L);
        ContenidoTablero contenidoTablero2 = new ContenidoTablero();
        contenidoTablero2.setId(contenidoTablero1.getId());
        assertThat(contenidoTablero1).isEqualTo(contenidoTablero2);
        contenidoTablero2.setId(2L);
        assertThat(contenidoTablero1).isNotEqualTo(contenidoTablero2);
        contenidoTablero1.setId(null);
        assertThat(contenidoTablero1).isNotEqualTo(contenidoTablero2);
    }
}
