package models.todo;


import com.avaje.ebean.Model;
import play.Logger;

import java.util.Date;
import java.util.List;

public class EbeanTaskDao implements TaskDao {

    Model.Finder<Integer, Task> find = new Model.Finder<>(Task.class);

    @Override
    public Task create(Task task) {
        task.insert();
        return task;
    }

    @Override
    public Task getById(int id) {
        return find.byId(id);
    }

    @Override
    public List<Task> getAll() {
        return find
                .orderBy("completeDate desc")
                .orderBy("created desc").findList();
    }

    @Override
    public List<Task> getTodo() {
        return find.where().eq("completed", false)
                .orderBy("created desc").findList();
    }

    @Override
    public List<Task> getCompleted() {
        return find.where().eq("completed", true)
                .orderBy("completeDate desc").findList();
    }

    @Override
    public Task complete(int id) {
        Task t = find.byId(id);
        if(t == null)
            return null;

        t.setCompleteDate(new Date());
        t.setCompleted(true);
        t.update();

        return t;
    }

    @Override
    public void delete(int id) {
        Task t = find.byId(id);
        if(t == null)
            return;

        t.delete();
    }
}
