package Model;

import java.io.Serializable;
import java.util.UUID;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ID;
    private String name;

    public Department(String name) {
        this.ID = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
