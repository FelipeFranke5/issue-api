package com.frankefelipee.myissuertracker.repository;

import com.frankefelipee.myissuertracker.entity.User;
import com.frankefelipee.myissuertracker.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_ReturnsSavedUser() {

        User user = User.builder()
                .name("test")
                .password("test").build();

        User savedUser = userRepository.save(user);
        Assertions.assertNotNull(savedUser);

    }

    @Test
    public void findAll_ReturnsAllUsers() {

        User user1 = User.builder()
                .name("test")
                .password("test").build();
        User user2 = User.builder()
                .name("test2")
                .password("test2").build();

        userRepository.save(user1);
        userRepository.save(user2);
        Assertions.assertEquals(2, userRepository.findAll().size());

    }

    @Test
    public void findById_ReturnsUserObject() {
        User user1 = User.builder()
                .name("test1")
                .password("test1").build();
        User user2 = User.builder()
                .name("test2")
                .password("test2").build();

        userRepository.save(user1);
        userRepository.save(user2);
        Optional<User> user1QueryResult = userRepository.findById(user1.getId());
        Optional<User> user2QueryResult = userRepository.findById(user2.getId());

        Assertions.assertTrue(user1QueryResult.isPresent());
        Assertions.assertTrue(user2QueryResult.isPresent());
    }

    @Test
    public void findById_InvalidId_ReturnsNotPresentOptional() {

        Optional<User> userQueryResult = userRepository.findById(UUID.randomUUID());
        Assertions.assertFalse(userQueryResult.isPresent());

    }

    @Test
    public void findById_NonExistingUserThrowsUserNotFoundException() {
        User user1 = User.builder()
                .name("test1")
                .password("test1").build();
        User user2 = User.builder()
                .name("test2")
                .password("test2").build();
        UUID randomId1 = UUID.randomUUID();

        userRepository.save(user1);
        userRepository.save(user2);

        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userRepository.findById(randomId1).orElseThrow(() -> new UserNotFoundException(randomId1.toString()))
        );

        Assertions.assertDoesNotThrow(() -> userRepository.findById(user1.getId()).orElseThrow(
                () -> new UserNotFoundException(user1.getId().toString())
        ));
    }

    @Test
    public void deleteById_RemovesUserFromDatabase() {

        User user1 = User.builder()
                .name("test1")
                .password("test1").build();

        User savedUser = userRepository.save(user1);
        userRepository.deleteById(savedUser.getId());
        List<User> users = userRepository.findAll();

        Assertions.assertEquals(0, users.size());

    }

    @Test
    public void deleteAll_RemovesAllUsersFromDatabase() {

        User user1 = User.builder()
                .name("test1")
                .password("test1").build();
        User user2 = User.builder()
                .name("test2")
                .password("test2").build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.deleteAll();

        List<User> emptyUsers = userRepository.findAll();
        Assertions.assertTrue(emptyUsers.isEmpty());

    }

}
