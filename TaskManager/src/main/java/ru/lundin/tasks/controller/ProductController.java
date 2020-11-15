package ru.lundin.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lundin.tasks.dao.ProductDao;
import ru.lundin.tasks.logic.DataFilter;
import ru.lundin.tasks.model.Filter;
import ru.lundin.tasks.model.Task;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author lundin
 */
@Controller
public class ProductController {
    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(value = "/add-tasks", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("product") Task task) {
        productDao.addTask(task);
        return "redirect:/get-tasks";
    }

    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public String addList(@ModelAttribute("product") Task.List list) {
        productDao.addList(list.name);
        return "redirect:/get-tasks";
    }

    @RequestMapping(value = "/complete-task", method = RequestMethod.POST)
    public String completeTask(@ModelAttribute("product") Task.ID id) {
        productDao.setComplete(id.id);
        return "redirect:/get-tasks";
    }

    @RequestMapping(value = "/complete-list", method = RequestMethod.POST)
    public String completeList(@ModelAttribute("product") Task.List list) {
        productDao.delList(list.name);
        return "redirect:/get-tasks";
    }

    @RequestMapping(value = "/get-tasks", method = RequestMethod.GET)
    public String getProducts(ModelMap map) {
        prepareModelMap(map, productDao.getTasks());
        return "index";
    }

    @RequestMapping(value = "/filter-tasks", method = RequestMethod.GET)
    public String getProducts(@RequestParam String filter, ModelMap map) {
        Optional<DataFilter> dataFilter = DataFilter.getFilterByName(filter);
        dataFilter.ifPresent(value -> prepareModelMap(map, value.filter(productDao)));

        return "index";
    }

    private void prepareModelMap(ModelMap map, List<Task> tasks) {
        map.addAttribute("tasks", tasks);
        map.addAttribute("task", new Task());
        map.addAttribute("list", new Task.List(""));
        map.addAttribute("filter", new Filter());
    }
}
