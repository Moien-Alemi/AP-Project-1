package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator{
    @Override
    public void validate(Entity entity) throws InvalidEntityException{
        if(!(entity instanceof Task)){
            throw new IllegalArgumentException("The input is not an instance of Task class.");
        }
        if(((Task) entity).getTitle().isEmpty() || ((Task) entity).getTitle() == null){
            throw new InvalidEntityException("Task's title should not be empty.");
        }
    }
}
