package com.codingseo.tddmembership.repositories.master;

import com.codingseo.tddmembership.entities.master.User;
import com.codingseo.tddmembership.entities.sub.MemberShip;
import com.codingseo.tddmembership.repositories.sub.MembershipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("멤버쉽등록")
    void 멤버쉽등록() {
        //given
        final User user = User.builder()
                .name("userID")
                .pass(1234)
                .build();

        //when
        final User result = userRepository.save(user);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("userID");
        assertThat(result.getPass()).isEqualTo(1234);

        //verify

    }
}
