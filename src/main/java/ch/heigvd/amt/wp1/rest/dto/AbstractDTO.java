package ch.heigvd.amt.wp1.rest.dto;

public abstract class AbstractDTO<PK> {

    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
