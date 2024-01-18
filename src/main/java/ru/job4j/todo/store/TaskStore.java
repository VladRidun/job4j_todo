package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskStore {
    Task create(Task task);

    void update(Task task);

    void delete(int taskId);

    List<Task> findAllOrderById();

    Optional<Task> findById(int taskId);

    List<Task> findAllByDone(boolean b);
}
