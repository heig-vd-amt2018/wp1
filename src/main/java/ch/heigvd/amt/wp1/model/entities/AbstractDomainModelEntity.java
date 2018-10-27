package ch.heigvd.amt.wp1.model.entities;

import java.io.Serializable;
import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractDomainModelEntity<PK> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof User || object instanceof Application) {
            AbstractDomainModelEntity other = (AbstractDomainModelEntity) object;

            return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
        }

        return false;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "[ id = " + id + " ]";
    }
}
