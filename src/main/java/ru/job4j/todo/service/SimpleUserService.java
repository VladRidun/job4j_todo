package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Collection;
import java.util.Optional;
import java.util.TimeZone;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private UserStore userStore;

    @Override
    public Optional<User> save(User user) {
        return userStore.save(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String email, String password) {
        return userStore.findByLoginAndPassword(email, password);
    }

    @Override
    public Collection<TimeZone> getTimeZones() {
        return userStore.getTimeZones();
    }
}
