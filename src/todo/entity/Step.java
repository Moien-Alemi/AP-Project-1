package todo.entity;

import db.Entity;
import java.util.Date;

public class Step extends Entity{
    public static final int STEP_ENTITY_CODE = 2;

    private String title;
    private Status status;
    private int taskRef;

    public enum Status{
        NotStarted,
        Completed;
    }

    public Step(String title, Status status, int taskRef) {
        this.title = title;
        this.status = status;
        this.taskRef = taskRef;
    }

    @Override
    public Step copy(){
        Step stepCopy = new Step(title, status, taskRef);
        stepCopy.id = this.id;
        return stepCopy;
    }

    @Override
    public int getEntityCode(){
        return STEP_ENTITY_CODE;
    }

    public String getTitle() {
        return title;
    }

    public Status getStatus() {
        return status;
    }


    public int getTaskRef() {
        return taskRef;
    }
}
