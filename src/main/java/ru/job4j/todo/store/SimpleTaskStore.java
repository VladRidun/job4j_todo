package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskStore implements TaskStore {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param task задача.
     * @return задача с id.
     */
    public Task create(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    /**
     * Обновить в базе задачу.
     *
     * @param task задача.
     */
    public void update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Удалить задачу по id.
     *
     * @param taskId ID
     */
    public void delete(int taskId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", taskId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Список задач отсортированных по id.
     *
     * @return список задач.
     */
    public List<Task> findAllOrderById() {
        Session session = sf.openSession();
        List<Task> tasksList = new ArrayList<>();
        try {
            session.beginTransaction();
            tasksList = session.createQuery("FROM Task ORDER BY id").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasksList;
    }

    /**
     * Найти задачу по ID
     *
     * @return задачу.
     */
    public Optional<Task> findById(int taskId) {
        Session session = sf.openSession();
        Optional<Task> optional;
        try {
            session.beginTransaction();
            optional = session.createQuery(
                            "FROM Task WHERE id = :fId", Task.class)
                    .setParameter("fId", taskId).uniqueResultOptional();
            session.getTransaction().commit();
            return optional;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    /**
     * Список задач по isDone
     *
     * @return список задач.
     */
    public List<Task> findAllByDone(boolean b) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Task> taskList = session.createQuery(
                            "from Task where done = :fDone", Task.class)
                    .setParameter("fDone", b)
                    .list();
            session.getTransaction().commit();
            return taskList;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }
}