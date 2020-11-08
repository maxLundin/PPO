package ru.lundin.tasks.model;

import java.util.Comparator;

/**
 * @author lundin
 */
public class Task {
    public static final PriceComparator PRICE_COMPARATOR = new PriceComparator();

    public static class ID {
        public int id;

        public ID(int id) {
            this.id = id;
        }
    }

    public static class List {
        public String name;

        public List(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    private final ID id;
    private List list;
    private String name;
    private String description;
    private boolean completed = false;

    private static class PriceComparator implements Comparator<Task> {
        public int compare(Task p1, Task p2) {
            return p1.id.id - p2.id.id;
        }
    }

    public Task() {
        id = new ID(-1);
        list = new List("");
    }

    public Task(int id, String name, String description, String list) {
        this.id = new ID(id);
        this.name = name;
        this.description = description;
        this.list = new List(list);
    }

    public int getId() {
        return id.id;
    }

    public void setId(int id) {
        this.id.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted() {
        completed = true;
    }

    public String getList() {
        return list.name;
    }

    public void setList(String list) {
        this.list.name = list;
    }
}
