package db;

import java.util.Date;

public abstract class Entity {
    public int id;
    protected Date creationDate;
    protected Date lastModificationDate;

    public abstract Entity copy();

    public abstract int getEntityCode();
}