package ch.heigvd.amt.wp1.rest.dto;

import ch.heigvd.amt.wp1.model.entities.AbstractDomainModelEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class ApplicationDTO {
    //! Creation date of the application.
    private Timestamp createdDate;

    //! Name of the application.
    private String name;

    //! Description of the application.
    private String description;

    //! ApplicationDTO key unique ID.
    private String apiKey;

    //! ApplicationDTO secret unique ID.
    private String apiSecret;

    public ApplicationDTO() {

    }

    public ApplicationDTO(
            Timestamp createdDate,
            String name,
            String description,
            String apiKey,
            String apiSecret
    ) {
        this.createdDate = createdDate;
        this.name = name;
        this.description = description;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    @PrePersist
    void onCreate() {
        this.setCreatedDate(new Timestamp((new Date()).getTime()));
        this.setApiKey(UUID.randomUUID().toString());
        this.setApiSecret(UUID.randomUUID().toString());
    }
}
