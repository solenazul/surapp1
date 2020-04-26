package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class IPSTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IPS.class);
        IPS iPS1 = new IPS();
        iPS1.setId(1L);
        IPS iPS2 = new IPS();
        iPS2.setId(iPS1.getId());
        assertThat(iPS1).isEqualTo(iPS2);
        iPS2.setId(2L);
        assertThat(iPS1).isNotEqualTo(iPS2);
        iPS1.setId(null);
        assertThat(iPS1).isNotEqualTo(iPS2);
    }
}
