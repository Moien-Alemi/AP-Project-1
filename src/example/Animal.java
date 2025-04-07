package example;

import db.*;

public class Animal extends Entity {
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
}
