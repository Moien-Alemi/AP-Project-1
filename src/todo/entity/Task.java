package todo.entity;

import db.*;
import java.util.Date;

public class Task extends Entity implements Trackable{
    public static final int TASK_ENTITY_CODE = 1;

    private String title;
    private String description;
    private  Date dueDate;
    private Status status;

    public enum Status{
        NotStarted,
        InProgress,
        Completed;
    }

    public Task(String title, String description, Date dueDate, Status status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    @Override
    public Task copy(){
        Task taskCopy = new Task(title, description, new Date(dueDate.getTime()), status);
        taskCopy.id = this.id;
        taskCopy.setCreationDate(new Date(creationDate.getTime()));
        taskCopy.setLastModificationDate(new Date(lastModificationDate.getTime()));
        return taskCopy;
    }

    @Override
    public int getEntityCode(){
        return TASK_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date){
        creationDate = date;
    }

    @Override
    public Date getCreationDate(){
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date){
        lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate(){
        return lastModificationDate;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public Status getStatus() {
        return status;
    }
}