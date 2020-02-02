package controllers;

import models.todo.Task;
import models.todo.TaskDao;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class TaskController extends Controller {

    private TaskDao taskDao;
    private FormFactory formFactory;

    @Inject
    public TaskController(TaskDao taskDao, FormFactory formFactory) {
        this.taskDao = taskDao;
        this.formFactory = formFactory;
    }

   
    public Result all() {
        return ok(Json.toJson(taskDao.getAll()));
    }

  
    public Result todo() {
        return ok(Json.toJson(taskDao.getTodo()));
    }
   
    public Result completed() {
        return ok(Json.toJson(taskDao.getCompleted()));
    }
    
    public Result get(int id) {
        return ok(Json.toJson(taskDao.getById(id)));
    }
    
    public Result put() {
        Form<Task> taskForm = formFactory.form(Task.class).bindFromRequest();

        if (taskForm.hasErrors())
            return badRequest(taskForm.errorsAsJson().toString());

        Task task = taskForm.get();

        task = taskDao.create(task);

        response().setHeader(LOCATION, routes.TaskController.get(task.getId()).absoluteURL(request()));
        return created(Json.toJson(task));
    }
   
    public Result complete(int id) {
        Task task = taskDao.complete(id);

        if(task == null)
            return notFound("Resource not found with id: "+id);

        return ok(Json.toJson(task));
    }
    
    public Result delete(int id) {
        taskDao.delete(id);
        return noContent();
    }
}
