package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StepService {
    private StepService() {}

    public static void saveStep(String title, int taskRef){
        try {
            Step newStep = new Step(title, Step.Status.NotStarted, taskRef);
            Database.add(newStep);
            System.out.println("Task saved successfully");
            System.out.println("ID: " + newStep.id);
            Date now = new Date();
            System.out.println("Creation date: " + now);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save step.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteStep(int id){
        Database.delete(id);
        System.out.println("Entity with ID = " + id + " successfully deleted.");
    }

    public static void updateStep(int ID, String field, String newValue){
        try {
            Step oldStep = (Step) Database.get(ID);
            Step newStep;
            field = field.toLowerCase();
            switch (field) {
                case "title":
                    newStep = new Step(newValue, oldStep.getStatus(), oldStep.getTaskRef());
                    newStep.id = oldStep.id;
                    Database.update(newStep);
                    System.out.println("Successfully updated the Step.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + oldStep.getTitle());
                    System.out.println("New value: " + newValue);
                    System.out.println("Modification Date: " + new Date());
                    System.out.println();
                    break;
                case "status":
                    Step.Status newStatus = Step.Status.valueOf(newValue);
                    newStep = new Step(oldStep.getTitle(), newStatus, oldStep.getTaskRef());
                    newStep.id = oldStep.id;
                    Database.update(newStep);
                    StepService.checkTaskStatus(newStep.getTaskRef());
                    System.out.println("Successfully updated the Step.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + oldStep.getStatus());
                    System.out.println("New value: " + newValue);
                    System.out.println("Modification Date: " + new Date());
                    System.out.println();
                    break;
                default:
                    throw new IllegalArgumentException("such a field doesn't exist or is not updatable.");
            }
        }
        catch (ClassCastException e){
            System.out.println("Can not update Step with ID = " + ID);
            System.out.println("Error: This entity is not Step");
        }
        catch (Exception e){
            System.out.println("Can not update Step with ID = " + ID);
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void checkTaskStatus(int taskID){
        ArrayList<Step> stepList = findAllSteps(taskID);

        boolean isNotStarted = true;
        boolean isComplete = true;
        for (Step s : stepList) {
            if (s.getStatus() == Step.Status.Completed) {
                isNotStarted = false;
                break;
            }
        }
        if (isNotStarted == false) {
            for(Step s : stepList) {
                if (s.getStatus() != Step.Status.Completed){
                    isComplete = false;
                    break;
                }
            }
        }

        if (isNotStarted == false ){
            if(isComplete == true){
                TaskService.updateTask(taskID, "status", "Completed");
            }else {
                TaskService.updateTask(taskID, "status", "InProgress");
            }
        }
    }

    public static ArrayList<Step> findAllSteps(int taskID){
        ArrayList<Step> stepList = new ArrayList<>();
        for (Entity entity : Database.getAll(Step.STEP_ENTITY_CODE)) {
            if (((Step) entity).getTaskRef() == taskID) {
                stepList.add(((Step) entity));
            }
        }
        return stepList;
    }

    public static void showStep(Step step){
        System.out.println("\t+ " + step.getTitle() + ":");
        System.out.println("\t\tID: " + step.id);
        System.out.println("\t\tStatus: " + step.getStatus());
    }

}
