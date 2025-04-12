package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskService {
    private TaskService() {}

    public static void saveTask(String title, String description, String dateString){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = format.parse(dateString);
            Task newTask = new Task(title, description, dueDate, Task.Status.NotStarted);
            Database.add(newTask);
            System.out.println("Task saved successfully");
            System.out.println("ID: " + newTask.id);
            Date now = new Date();
            System.out.println("Creation date: " + now);
        } catch (Exception e) {
            System.out.println("Cannot save task.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void delete(int id){
        try {
            Entity entity = Database.get(id);
            if(entity instanceof Task){
                Database.delete(id);
                for(Entity taskStep : Database.getAll(Step.STEP_ENTITY_CODE)) {
                    if (((Step) taskStep).getTaskRef() == id) {
                        Database.delete(taskStep.id);
                    }
                }
                System.out.println("Entity with ID = " + id + " successfully deleted.");
            }
            if(entity instanceof Step) {
                StepService.deleteStep(id);
            }
        }catch (Exception e) {
            System.out.println("Can not delete entity with ID = " + id);
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateTask(int ID, String field, String newValue){
        try {
            Task oldTask = (Task) Database.get(ID);
            Task newTask;
            field = field.toLowerCase();
            switch (field) {
                case "title":
                    newTask = new Task(newValue, oldTask.getDescription(), oldTask.getDueDate(), oldTask.getStatus());
                    Database.update(newTask);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + oldTask.getTitle());
                    System.out.println("New value: " + newValue);
                    System.out.println("Modification Date: " + new Date());
                    System.out.println();
                    break;
                case "description":
                    newTask = new Task(oldTask.getTitle(), newValue, oldTask.getDueDate(), oldTask.getStatus());
                    Database.update(newTask);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + oldTask.getDescription());
                    System.out.println("New value: " + newValue);
                    System.out.println("Modification Date: " + new Date());
                    System.out.println();
                    break;
                case "due date":
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date newDueDate = format.parse(newValue);
                    newTask = new Task(oldTask.getTitle(), oldTask.getDescription(), newDueDate, oldTask.getStatus());
                    Database.update(newTask);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + oldTask.getDueDate());
                    System.out.println("New value: " + newValue);
                    System.out.println("Modification Date: " + new Date());
                    System.out.println();
                    break;
                case "status":
                    newValue = newValue.toUpperCase();
                    Task.Status newStatus = Task.Status.valueOf(newValue);
                    newTask = new Task(oldTask.getTitle(), oldTask.getDescription(), oldTask.getDueDate(), newStatus);
                    Database.update(newTask);
                    checkTaskStatus(newTask);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + oldTask.getStatus());
                    System.out.println("New value: " + newValue);
                    System.out.println("Modification Date: " + new Date());
                    System.out.println();
                    break;
                default:
                    throw new IllegalArgumentException("such a field doesn't exist.");
            }
        }catch (ParseException e) {
            System.out.println("Can not update task with ID = " + ID);
            System.out.println("Error: date format is incorrect.");
        }
        catch (ClassCastException e){
            System.out.println("Can not update task with ID = " + ID);
            System.out.println("Error: This entity is not Task");
        }
        catch (Exception e){
            System.out.println("Can not update task with ID = " + ID);
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void checkTaskStatus(Task task){
        if(task.getStatus() == Task.Status.Completed){
            for(Entity entity : Database.getAll(Step.STEP_ENTITY_CODE)){
                if(((Step) entity).getTaskRef() == task.id){
                    StepService.updateStep(entity.id, "status", "Completed");
                }
            }
        }
    }
}
