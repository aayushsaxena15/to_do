package models.todo;

import java.util.List;

public interface TaskDao {
    Task create(Task task);
    Task getById(int id);
    List<Task> getAll();
    List<Task> getTodo();
    List<Task> getCompleted();
    Task complete(int id);
    void delete(int id);
}
