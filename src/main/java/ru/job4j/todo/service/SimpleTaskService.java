package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private TaskStore taskStore;

    @Override
    public Task create(Task task) {
        return taskStore.create(task);
    }

    @Override
    public void update(Task task) {
        taskStore.update(task);
    }

    @Override
    public void delete(int taskId) {
        taskStore.delete(taskId);
    }

    @Override
    public List<Task> findAllOrderById() {
        return taskStore.findAllOrderById();
    }

    @Override
    public Optional<Task> findById(int taskId) {
        return taskStore.findById(taskId);
    }

    @Override
    public List<Task> findAllByDone(boolean b) {
        return taskStore.findAllByDone(b);
    }
}
