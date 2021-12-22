package com.codingseo.tddmembership.repositories.master;

import com.codingseo.tddmembership.configs.datasources.MasterDataSourceConfig;
import com.codingseo.tddmembership.entities.master.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ImportAutoConfiguration(MasterDataSourceConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저등록")
    void 유저등록() {
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

    @Test
    @DisplayName("유저등록이후_조회")
    void 유저등록이후_조회() {
        //given
        final User user = User.builder()
                .name("userFind")
                .pass(1234)
                .build();

        //when
        userRepository.save(user);
        final User findResult = userRepository.findByName("userFind");

        //then
        assertThat(findResult.getId()).isNotNull();
        assertThat(findResult.getName()).isEqualTo("userFind");
        assertThat(findResult.getPass()).isEqualTo(1234);


    }
}
