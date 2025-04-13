package todo.service;

import db.Database;
import db.Entity;
import todo.entity.Step;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
                for(Entity taskStep : StepService.findAllSteps(id)) {
                    Database.delete(taskStep.id);
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
                    newTask.id = oldTask.id;
                    newTask.setCreationDate(oldTask.getCreationDate());
                    newTask.setLastModificationDate(oldTask.getLastModificationDate());
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
                    newTask.id = oldTask.id;
                    newTask.setCreationDate(oldTask.getCreationDate());
                    newTask.setLastModificationDate(oldTask.getLastModificationDate());
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
                    newTask.id = oldTask.id;
                    newTask.setCreationDate(oldTask.getCreationDate());
                    newTask.setLastModificationDate(oldTask.getLastModificationDate());
                    Database.update(newTask);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + oldTask.getDueDate());
                    System.out.println("New value: " + newValue);
                    System.out.println("Modification Date: " + new Date());
                    System.out.println();
                    break;
                case "status":
                    Task.Status newStatus = Task.Status.valueOf(newValue);
                    newTask = new Task(oldTask.getTitle(), oldTask.getDescription(), oldTask.getDueDate(), newStatus);
                    newTask.id = oldTask.id;
                    newTask.setCreationDate(oldTask.getCreationDate());
                    newTask.setLastModificationDate(oldTask.getLastModificationDate());
                    Database.update(newTask);
                    TaskService.checkTaskStatus(newTask);
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
            for(Step entity : StepService.findAllSteps(task.id)){
                if (entity.getStatus() != Step.Status.Completed) {
                    StepService.updateStep(entity.id, "status", "Completed");
                }
            }
        }
    }

    public static void getTask(int ID){
        try {
            Task task = (Task) Database.get(ID);
            showTask(task);
        } catch (Exception e) {
            System.out.println("Can not get task.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void showTask(Task task){
        System.out.println();
        System.out.println("ID: " + task.id);
        System.out.println("Title: " + task.getTitle());
        System.out.println("Due Date: " + task.getDueDate());
        System.out.println("Status: " + task.getStatus());
        ArrayList<Step> stepList = StepService.findAllSteps(task.id);
        if(stepList.size() != 0){
            System.out.println("Steps:");
            for(Step step : stepList){
                StepService.showStep(step);
            }
        }
        System.out.println();

    }

    public static void getAllTask(){
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Entity entity : Database.getAll(Task.TASK_ENTITY_CODE)){
            allTasks.add((Task) entity);
        }

        allTasks.sort(Comparator.comparing(Task :: getDueDate));

        for (Task task : allTasks){
            showTask(task);
        }
    }

    public static void getIncompleteTasks(){
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        for (Entity entity : Database.getAll(Task.TASK_ENTITY_CODE)){
            if (((Task) entity).getStatus() != Task.Status.Completed) {
                incompleteTasks.add((Task) entity);
            }
        }

        incompleteTasks.sort(Comparator.comparing(Task :: getDueDate));

        for (Task task : incompleteTasks){
            showTask(task);
        }
    }
}
