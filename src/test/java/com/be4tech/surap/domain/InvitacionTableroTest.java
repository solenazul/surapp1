package com.be4tech.surap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.be4tech.surap.web.rest.TestUtil;

public class InvitacionTableroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitacionTablero.class);
        InvitacionTablero invitacionTablero1 = new InvitacionTablero();
        invitacionTablero1.setId(1L);
        InvitacionTablero invitacionTablero2 = new InvitacionTablero();
        invitacionTablero2.setId(invitacionTablero1.getId());
        assertThat(invitacionTablero1).isEqualTo(invitacionTablero2);
        invitacionTablero2.setId(2L);
        assertThat(invitacionTablero1).isNotEqualTo(invitacionTablero2);
        invitacionTablero1.setId(null);
        assertThat(invitacionTablero1).isNotEqualTo(invitacionTablero2);
    }
}
