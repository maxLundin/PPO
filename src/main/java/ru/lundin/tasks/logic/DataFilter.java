package ru.lundin.tasks.logic;

import ru.lundin.tasks.dao.ProductDao;
import ru.lundin.tasks.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author lundin
 */
public abstract class DataFilter {
    private static Map<String, DataFilter> filters = createFiltersMap();

    private static HashMap<String, DataFilter> createFiltersMap() {
        HashMap<String, DataFilter> filters = new HashMap<>();
        filters.put("All", new AllFilter());
        filters.put("Completed", new CompletedFilter());
        filters.put("Uncompleted", new UncompletedFilter());
        return filters;
    }

    public abstract List<Task> filter(ProductDao productDao);

    private static class AllFilter extends DataFilter {
        public List<Task> filter(ProductDao productDao) {
            return productDao.getTasks();
        }
    }

    private static class CompletedFilter extends DataFilter {
        public List<Task> filter(ProductDao productDao) {
            return productDao.getCompletedTasks();
        }
    }

    private static class UncompletedFilter extends DataFilter {
        public List<Task> filter(ProductDao productDao) {
            return productDao.getUnfinishedTasks();
        }
    }

    public static Optional<DataFilter> getFilterByName(String name) {
        return Optional.ofNullable(filters.get(name));
    }
}
