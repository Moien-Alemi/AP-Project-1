package example;

import db.*;

public class Animal extends Entity {
    final int ANIMAL_ENTITY_CODE = 20;

    public String name;
    public int numberOfLegs;

    public Animal(String name, int numberOfLegs){
        this.name = name;
        this.numberOfLegs = numberOfLegs;
    }

    @Override
    public Animal copy(){
        Animal animalCopy = new Animal(this.name, this.numberOfLegs);
        animalCopy.id = this.id;

        return animalCopy;
    }

    @Override
    public int getEntityCode() {
        return ANIMAL_ENTITY_CODE;
    }
}
