package ch.heigvd.amt.wp1.model;

import java.util.Date;
import java.util.List;

public class Application {
    private final Date creationDate;
    private final String name;
    private final String description;

    public Application(Date creationDate, String name, String description) {
        this.creationDate = creationDate;
        this.name = name;
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
