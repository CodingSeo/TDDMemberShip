package com.codingseo.tddmembership.repositories.sub;

import com.codingseo.tddmembership.entities.sub.MemberShip;
import com.codingseo.tddmembership.repositories.sub.MembershipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class
MembershipRepositoryTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    @DisplayName("멤버쉽등록")
    void 멤버쉽등록() {
        //given
        final MemberShip memberShip = MemberShip.builder()
                .membershipName("네이버")
                .point(100000)
                .build();

        //when
        final MemberShip result = membershipRepository.save(memberShip);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getMembershipName()).isEqualTo("네이버");
        assertThat(result.getPoint()).isEqualTo(100000);

    }
}
