package ch.heigvd.amt.wp1.model.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "application")
@NamedQueries({
        @NamedQuery(name = "Application.findByName", query = "SELECT c FROM Application c WHERE c.name = :name"),
})
public class Application extends AbstractDomainModelEntity<Long> {
    //! Creation date of the application.
    @Column(name="created_date", updatable = false, nullable = false)
    private Timestamp createdDate;

    //! Name of the application.
    @Column(name="name", nullable = false)
    private String name;

    //! Description of the application.
    @Column(name="description")
    private String description;

    //! ApplicationDTO key unique ID.
    @Column(name="api_key", updatable = false, nullable = false)
    private String apiKey;

    //! ApplicationDTO secret unique ID.
    @Column(name="api_secret", updatable = false, nullable = false)
    private String apiSecret;

    public Application() {
        // Only here for JPA
    }

    public Application(
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
