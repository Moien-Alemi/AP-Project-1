package example;

import db.*;
import db.exception.InvalidEntityException;

public class AnimalValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(!(entity instanceof Animal)){
            throw  new IllegalArgumentException("This object is not an instance of Animal Class.");
        }

        Animal animal = (Animal) entity;

        if(animal.numberOfLegs <= 0){
            throw new InvalidEntityException("legs' number must be a positive integer");
        }
        if(animal.name.isEmpty() || animal.name == null){
            throw new InvalidEntityException("Name field is empty");
        }
    }
}