package com.example.shop.repository;

import com.example.shop.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    User user =
            User.builder()
                    .id(1L)
                    .name("Luk")
                    .password("1111")
                    .userName("luk111")
                    .age(22)
                    .roles(new ArrayList<>())
                    .isDeleted(false)
                    .build();

    @Test
    void findByUserName() {
        userRepo.save(user);

        User user = userRepo.findByUserNameAndIsDeletedIsFalse("luk111");
        Assertions.assertThat(user.getId()).isGreaterThan(0);
        Assertions.assertThat(user.getName()).isEqualTo("Luk");
        Assertions.assertThat(user.getAge()).isEqualTo(22);
    }

    @Test
    void findAll() {
        List<User> users = new ArrayList<>();
        Iterable<User> source =userRepo.findAll();
        source.forEach(users::add);
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void saveUserTest (){

        userRepo.save(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
        Assertions.assertThat(user.getName()).isEqualTo("Luk");
        Assertions.assertThat(user.getAge()).isEqualTo(22);
    }
}