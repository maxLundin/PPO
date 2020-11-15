package ru.lundin.tasks.dao;

import ru.lundin.tasks.model.Task;

import java.util.List;

/**
 * @author lundin
 */
public interface ProductDao {
    int addTask(Task task);

    void addList(String list);

    void delList(String list);

    void setComplete(int id);

    List<Task> getTasks();

    List<Task> getCompletedTasks();

    List<Task> getUnfinishedTasks();
}
