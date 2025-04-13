import db.Database;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.*;
import todo.validator.*;

import java.util.Scanner;

public class Main {
      public static void main(String[] args){
        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());
        Scanner scn = new Scanner(System.in);

        boolean running = true;
        String command;

        while(running == true) {
            System.out.println();
            System.out.println("### Main menu ###");
            System.out.println("Please enter one of the following commands.");
            System.out.println("1. add task");
            System.out.println("2. add step");
            System.out.println("3. delete");
            System.out.println("4. update task");
            System.out.println("5. update step");
            System.out.println("6. get task-by-id");
            System.out.println("7. get all-tasks");;
            System.out.println("8. get incomplete-tasks");
            System.out.println("9. exit");

            try {
                command = scn.nextLine().toLowerCase();

                switch (command) {
                    case "add task":
                        addTask(scn);
                        break;
                    case "add step":
                        addStep(scn);
                        break;
                    case "delete":
                        delete(scn);
                        break;
                    case "update task":
                        updateTask(scn);
                        break;
                    case "update step":
                        updateStep(scn);
                        break;
                    case "get task-by-id":
                        getTaskByID(scn);
                        break;
                    case "get all-tasks":
                        getAllTask();
                        break;
                    case "get incomplete-tasks":
                        getIncompleteTasks();
                        break;
                    case "exit":
                        running = false;
                        break;
                    default:
                        throw new IllegalArgumentException("This command doesn't exist in the command list.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println();
            }

        }
    }

    private static void updateStep(Scanner scn) {
        System.out.println("Please enter Step ID.");
        System.out.print("ID : ");
        int ID = scn.nextInt();
        scn.nextLine();

        System.out.println("Which field do you want to update?");
        System.out.print("Field : ");
        String field = scn.nextLine();

        System.out.println("Please enter field's new value.");
        System.out.print("New value : ");
        String newValue = scn.nextLine();

        StepService.updateStep(ID, field, newValue);
    }

    private static void updateTask(Scanner scn) {
        System.out.println("Please enter task ID.");
        System.out.print("ID : ");
        int ID = scn.nextInt();
        scn.nextLine();

        System.out.println("Which field do you want to update?");
        System.out.print("Field : ");
        String field = scn.nextLine();

        System.out.println("Please enter field's new value.");
        System.out.print("New value : ");
        String newValue = scn.nextLine();

        TaskService.updateTask(ID, field, newValue);
    }

    private static void delete(Scanner scn) {
        System.out.println("Please enter ID of the entity you want to delete.");
        System.out.print("ID: ");
        int ID = scn.nextInt();
        scn.nextLine();
        TaskService.delete(ID);
        System.out.println();
    }

    private static void addStep(Scanner scn) {
        System.out.println("Please enter step details.");
        System.out.print("Title : ");
        String title = scn.nextLine();

        System.out.print("Task id : ");
        int taskRef = scn.nextInt();
        scn.nextLine();

        System.out.println();
        StepService.saveStep(title, taskRef);
        System.out.println();
    }

    private static void addTask(Scanner scn) {
        System.out.println("Please enter task details.");
        System.out.print("Title : ");
        String title = scn.nextLine();

        System.out.print("Description : ");
        String description = scn.nextLine();

        System.out.println("(Please enter date with this format : yyyy-mm-dd)");
        System.out.print("Due date : ");
        String dateString = scn.nextLine();

        System.out.println();
        TaskService.saveTask(title, description, dateString);
        System.out.println();
    }

    public static void getTaskByID(Scanner scn){
        System.out.println("Please enter Task ID.");
        System.out.print("ID : ");
        int ID = scn.nextInt();
        scn.nextLine();
        TaskService.getTask(ID);
    }

    public static void getAllTask(){
        TaskService.getAllTask();
    }

    public static void getIncompleteTasks(){
          TaskService.getIncompleteTasks();
    }
}