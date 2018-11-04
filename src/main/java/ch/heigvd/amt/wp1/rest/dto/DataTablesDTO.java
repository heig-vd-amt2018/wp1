package ch.heigvd.amt.wp1.rest.dto;

import java.util.List;

public class DataTablesDTO {

    private long draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<ApplicationDTO> data;

    public DataTablesDTO() {

    }

    public DataTablesDTO(long draw, long recordsTotal, List<ApplicationDTO> data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
        this.data = data;
    }

    public long getDraw() {
        return draw;
    }

    public void setDraw(long draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<ApplicationDTO> getData() {
        return data;
    }

    public void setData(List<ApplicationDTO> data) {
        this.data = data;
    }
}
