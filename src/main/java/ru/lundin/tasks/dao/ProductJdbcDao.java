package ru.lundin.tasks.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import ru.lundin.tasks.model.Task;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * @author lundin
 */
public class ProductJdbcDao extends JdbcDaoSupport implements ProductDao {

    public ProductJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public int addTask(Task task) {
        String sql = "INSERT INTO PRODUCT (NAME, DESCR, COMPLETED, LIST) VALUES (?, ?, ?, ?)";
        return getJdbcTemplate().update(sql, task.getName(), task.getDescription(), false, task.getList());
    }

    @Override
    public void addList(String list) {
        String sql = "INSERT INTO LIST (NAME) VALUES (?)";
        getJdbcTemplate().update(sql, list);
    }

    @Override
    public void delList(String list) {
        String sql = "DELETE FROM PRODUCT where LIST == " + list;
        getJdbcTemplate().update(sql);
    }

    @Override
    public void setComplete(int id) {
        String sql = "UPDATE PRODUCT SET COMPLETED = true where ID == " + id;
        getJdbcTemplate().update(sql);
    }

    @Override
    public List<Task> getTasks() {
        String sql = "SELECT * FROM PRODUCT";
        return getProductsByRequest(sql);
    }

    @Override
    public List<Task> getCompletedTasks() {
        String sql = "SELECT NAME, DESCR FROM PRODUCT ORDER WHERE COMPLETED == true";
        return getProductsByRequest(sql);
    }

    @Override
    public List<Task> getUnfinishedTasks() {
        String sql = "SELECT NAME, DESCR FROM PRODUCT ORDER WHERE COMPLETED == false";
        return getProductsByRequest(sql);

    }

    private List<Task> getProductsByRequest(String sql) {
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Task.class));
    }

}
