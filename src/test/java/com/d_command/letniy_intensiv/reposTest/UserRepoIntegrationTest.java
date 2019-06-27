package com.d_command.letniy_intensiv.reposTest;

import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.UserRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepoIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User alex = new User();
        alex.setUsername("alex");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        User found = userRepo.findByUsername("alex");

        // then
        Assert.assertEquals(found.getId(), alex.getId());
    }

    @Test
    public void whenFundByWrongName_thenReturnNull() {
        // when
        User found = userRepo.findByUsername("unknown");

        // then
        Assert.assertNull(found);
    }
}
