package db;

import java.util.ArrayList;
import db.exception.*;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<Entity>();
    private static int counter = 0;

    private Database() {}

    public static void add(Entity e){
        counter++;
        e.id = counter;
        entities.add(e.copy());
    }

    public static Entity get(int id){
        for (Entity entity : entities){
            if(entity.id == id){
                return entity.copy();
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id){
        for (Entity entity : entities){
            if(entity.id == id){
                entities.remove(entity);
                return;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void update(Entity e){
        for (Entity entity : entities){
            if(entity.id == e.id){
                entities.set(entities.indexOf(entity), e.copy());
                return;
            }
        }
        throw new EntityNotFoundException();
    }

}
