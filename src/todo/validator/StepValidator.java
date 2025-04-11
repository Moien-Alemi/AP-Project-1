package todo.validator;

import db.Entity;
import db.Database;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepValidator implements Validator{
    @Override
    public void validate(Entity entity) throws InvalidEntityException{
        if(!(entity instanceof Step)){
            throw new IllegalArgumentException("The input is not an instance of Step class.");
        }

        if(((Step) entity).getTitle().isEmpty() || ((Step) entity).getTitle() == null){
            throw new InvalidEntityException("Step's title should not be empty.");
        }

        for(Entity e : Database.getAll(Task.TASK_ENTITY_CODE)){
            if(e.id == ((Step) entity).getTaskRef()){
                return;
            }
        }
        throw new InvalidEntityException("There is no task with this id in the Database.");
    }
}
