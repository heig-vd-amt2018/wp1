package ch.heigvd.amt.wp1.rest.dto;

import ch.heigvd.amt.wp1.model.entities.Application;

import java.sql.Timestamp;

public class ApplicationDTO extends AbstractDTO<Long> {

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
            String name,
            String description
    ) {
        this.name = name;
        this.description = description;
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

    public void fromEntity(Application app) {
        this.setId(app.getId());
        this.setCreatedDate(app.getCreatedDate());
        this.setName(app.getName());
        this.setDescription(app.getDescription());
        this.setApiKey(app.getApiKey());
        this.setApiSecret(app.getApiSecret());
    }
}
