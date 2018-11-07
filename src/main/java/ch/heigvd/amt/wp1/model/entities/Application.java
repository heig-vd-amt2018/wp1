package ch.heigvd.amt.wp1.model.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "application")
@NamedQueries({
        @NamedQuery(name = "Application.findByIdByUser", query = "SELECT a FROM Application a WHERE a.id = :id AND a.owner = :owner"),
        @NamedQuery(name = "Application.findByNameByUser", query = "SELECT a FROM Application a WHERE a.name = :name AND a.owner = :owner"),
        @NamedQuery(name = "Application.findAllByUser", query = "SELECT a FROM Application a WHERE a.owner = :owner"),
})
public class Application extends AbstractDomainModelEntity<Long> {
    //! The developer who made the application.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    //! Creation date of the application.
    @Column(name = "created_date", updatable = false, nullable = false)
    private Timestamp createdDate;

    //! Name of the application.
    @Column(name = "name", nullable = false)
    private String name;

    //! Description of the application.
    @Column(name = "description")
    private String description;

    //! ApplicationDTO key unique ID.
    @Column(name = "api_key", updatable = false, nullable = false)
    private String apiKey;

    //! ApplicationDTO secret unique ID.
    @Column(name = "api_secret", updatable = false, nullable = false)
    private String apiSecret;

    public Application() {
        // Only here for JPA
    }

    public Application(
            User owner,
            String name,
            String description
    ) {
        this.owner = owner;
        this.createdDate = new Timestamp((new Date()).getTime());
        this.name = name;
        this.description = description;
        this.apiKey = UUID.randomUUID().toString();
        this.apiSecret = UUID.randomUUID().toString();

        owner.addOwnedApplication(this);
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
}
