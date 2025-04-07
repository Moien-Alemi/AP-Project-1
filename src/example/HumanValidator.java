package example;

import db.*;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Human)){
            throw  new IllegalArgumentException("This object is not an instance of Human Class.");
        }

        Human human = (Human) entity;

        if(human.age <= 0){
            throw new InvalidEntityException("Age must be a positive integer");
        }
        if(human.name.isEmpty() || human.name == null){
            throw new InvalidEntityException("Name field is empty");
        }
    }
}