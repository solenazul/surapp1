package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class TablerosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tableros.class);
        Tableros tableros1 = new Tableros();
        tableros1.setId(1L);
        Tableros tableros2 = new Tableros();
        tableros2.setId(tableros1.getId());
        assertThat(tableros1).isEqualTo(tableros2);
        tableros2.setId(2L);
        assertThat(tableros1).isNotEqualTo(tableros2);
        tableros1.setId(null);
        assertThat(tableros1).isNotEqualTo(tableros2);
    }
}
