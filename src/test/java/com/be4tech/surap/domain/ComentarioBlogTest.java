package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class ComentarioBlogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentarioBlog.class);
        ComentarioBlog comentarioBlog1 = new ComentarioBlog();
        comentarioBlog1.setId(1L);
        ComentarioBlog comentarioBlog2 = new ComentarioBlog();
        comentarioBlog2.setId(comentarioBlog1.getId());
        assertThat(comentarioBlog1).isEqualTo(comentarioBlog2);
        comentarioBlog2.setId(2L);
        assertThat(comentarioBlog1).isNotEqualTo(comentarioBlog2);
        comentarioBlog1.setId(null);
        assertThat(comentarioBlog1).isNotEqualTo(comentarioBlog2);
    }
}
