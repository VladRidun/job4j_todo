package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskStore implements TaskStore {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param task задача.
     * @return задача с id.
     */
    public Task create(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    /**
     * Обновить в базе задачу.
     *
     * @param task задача.
     */
    public void update(Task task) {
        crudRepository.run(session -> session.merge(task));
    }

    /**
     * Удалить задачу по id.
     *
     * @param taskId ID
     */
    public void delete(int taskId) {
        crudRepository.run(
                "delete from Task where id = :fId",
                Map.of("fId", taskId)
        );
    }

    /**
     * Список задач отсортированных по id.
     *
     * @return список задач.
     */
    public List<Task> findAllOrderById() {
        return crudRepository.query("select distinct t from Task t JOIN FETCH t.priority JOIN FETCH t.categories order by t.id", Task.class);
    }

    /**
     * Найти задачу по ID
     *
     * @return задачу.
     */
    public Optional<Task> findById(int taskId) {
        return crudRepository.optional("FROM Task WHERE id = :fId", Task.class,
                Map.of("fId", taskId)
        );
    }

    /**
     * Список задач по isDone
     *
     * @return список задач.
     */
    public List<Task> findAllByDone(boolean b) {
        return crudRepository.query(
                "from Task where done = :fDone", Task.class,
                Map.of("fDone", b)
        );
    }
}