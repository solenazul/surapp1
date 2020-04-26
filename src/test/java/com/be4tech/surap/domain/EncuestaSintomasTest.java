package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class EncuestaSintomasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncuestaSintomas.class);
        EncuestaSintomas encuestaSintomas1 = new EncuestaSintomas();
        encuestaSintomas1.setId(1L);
        EncuestaSintomas encuestaSintomas2 = new EncuestaSintomas();
        encuestaSintomas2.setId(encuestaSintomas1.getId());
        assertThat(encuestaSintomas1).isEqualTo(encuestaSintomas2);
        encuestaSintomas2.setId(2L);
        assertThat(encuestaSintomas1).isNotEqualTo(encuestaSintomas2);
        encuestaSintomas1.setId(null);
        assertThat(encuestaSintomas1).isNotEqualTo(encuestaSintomas2);
    }
}
