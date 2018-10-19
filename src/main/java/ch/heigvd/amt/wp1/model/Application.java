package ch.heigvd.amt.wp1.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Application {
    private final int id;
    private final Date creationDate;
    private String name;
    private String description;
    private final UUID apiKey;
    private final UUID apiSecret;

    public Application(int id, Date creationDate, String name, String description, UUID apiKey, UUID apiSecret) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.description = description;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public int getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getApiKey() {
        return apiKey;
    }

    public UUID getApiSecret() {
        return apiSecret;
    }
}
