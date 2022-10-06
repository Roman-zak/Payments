package models;

import java.io.Serializable;

public enum Role implements Serializable {
    USER(1), ADMIN(2);
    int id;

    Role(int i) {
        id=i;
    }

    public int getId() {
        return id;
    }
}
