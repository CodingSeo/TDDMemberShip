package com.codingseo.tddmembership.repositories.sub;

import com.codingseo.tddmembership.configs.datasources.MasterDataSourceConfig;
import com.codingseo.tddmembership.configs.datasources.SubDataSourceConfig;
import com.codingseo.tddmembership.entities.sub.MemberShip;
import com.codingseo.tddmembership.enums.MemberShipType;
import com.codingseo.tddmembership.repositories.sub.MembershipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ImportAutoConfiguration(SubDataSourceConfig.class)
public class
MembershipRepositoryTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    @DisplayName("멤버쉽등록")
    void 멤버쉽등록() {
        //given
        final MemberShip memberShip = MemberShip.builder()
                .userNo(1)
                .membershipName(MemberShipType.NAVER)
                .point(100000)
                .build();

        //when
        final MemberShip result = membershipRepository.save(memberShip);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserNo()).isEqualTo(1);
        assertThat(result.getMembershipName()).isEqualTo(MemberShipType.NAVER);
        assertThat(result.getPoint()).isEqualTo(100000);

    }

    @Test
    @DisplayName("멤버십이존재하는지테스트")
    void 멤버십이존재하는지테스트() {
        //given
        final MemberShip memberShip = MemberShip.builder()
                .userNo(2)
                .membershipName(MemberShipType.KAKAO)
                .point(100)
                .build();
        //when
        membershipRepository.save(memberShip);
        final MemberShip findResult = membershipRepository.findByUserNo(2);

        //then
        assertThat(findResult.getId()).isNotNull();
        assertThat(findResult.getUserNo()).isEqualTo(2);
        assertThat(findResult.getMembershipName()).isEqualTo(MemberShipType.KAKAO);
        assertThat(findResult.getPoint()).isEqualTo(100);
    }


    @Test
    @DisplayName("존재하지않는_멤버_조회")
    void 존재하지않는_멤버_조회() {
        //given
        final MemberShip memberShip = MemberShip.builder()
                .userNo(3)
                .membershipName(MemberShipType.KAKAO)
                .point(100)
                .build();
        //when
        membershipRepository.save(memberShip);
        final MemberShip findResult = membershipRepository.findByUserNo(4);

        //then
        assertThat(findResult).isNull();
    }
}
