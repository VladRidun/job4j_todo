package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SimpleUserStore implements UserStore {
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
            return  crudRepository.optional(
                    "FROM User WHERE login = :fLogin AND password = :fPassword", User.class,
                    Map.of("fLogin", login, "fPassword", password));
    }
}