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
        if(((Task) entity).title.isEmpty() || ((Task) entity).title == null){
            throw new InvalidEntityException("Task's title should not be empty.");
        }
    }
}
