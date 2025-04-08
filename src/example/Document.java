package example;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Document extends Entity implements Trackable {
    public static final int DOCUMENT_ENTITY_CODE = 9;

    public String content;

    public Document(String content){
        this.content = content;
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

    @Override
    public Document copy(){
        Document documentCopy = new Document(content);
        documentCopy.setCreationDate(new Date(this.creationDate.getTime()));
        documentCopy.setLastModificationDate(new Date(this.lastModificationDate.getTime()));
        documentCopy.id = this.id;

        return documentCopy;
    }

    @Override
    public int getEntityCode(){
        return DOCUMENT_ENTITY_CODE;
    }
}
