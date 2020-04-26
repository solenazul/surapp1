package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class EncuestaEntornoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncuestaEntorno.class);
        EncuestaEntorno encuestaEntorno1 = new EncuestaEntorno();
        encuestaEntorno1.setId(1L);
        EncuestaEntorno encuestaEntorno2 = new EncuestaEntorno();
        encuestaEntorno2.setId(encuestaEntorno1.getId());
        assertThat(encuestaEntorno1).isEqualTo(encuestaEntorno2);
        encuestaEntorno2.setId(2L);
        assertThat(encuestaEntorno1).isNotEqualTo(encuestaEntorno2);
        encuestaEntorno1.setId(null);
        assertThat(encuestaEntorno1).isNotEqualTo(encuestaEntorno2);
    }
}
