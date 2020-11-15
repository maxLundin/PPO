package ru.lundin.tasks.dao;

import ru.lundin.tasks.model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author lundin
 */
public class ProductInMemoryDao implements ProductDao {
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<String> lists = new CopyOnWriteArrayList<>();
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    public void addList(String list) {
        synchronized (lists) {
            lists.add(list);
        }
    }

    @Override
    public void delList(String list) {
        for (Task task : tasks) {
            if (task.getList().equals(list)) {
                task.setCompleted();
            }
        }
    }

    public int addTask(Task task) {
        if (!lists.contains(task.getList())) {
            return -1;
//            throw new IllegalArgumentException("No such list");
        }
        int id = lastId.incrementAndGet();
        task.setId(id);
        tasks.add(task);
        tasks.sort(Comparator.comparing(Task::getList).thenComparing(Task::getId));
        return id;
    }

    @Override
    public void setComplete(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted();
            }
        }
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    public List<Task> getCompletedTasks() {
        return tasks.stream().filter(Task::getCompleted).collect(Collectors.toList());
    }

    public List<Task> getUnfinishedTasks() {
        return tasks.stream().filter((task) -> !task.getCompleted()).collect(Collectors.toList());
    }
}
