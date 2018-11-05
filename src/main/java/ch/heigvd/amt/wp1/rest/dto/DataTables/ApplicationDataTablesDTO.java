package ch.heigvd.amt.wp1.rest.dto.DataTables;

import ch.heigvd.amt.wp1.rest.dto.ApplicationDTO;

import java.util.List;

public class ApplicationDataTablesDTO extends DataTablesDTO<ApplicationDTO> {

    public ApplicationDataTablesDTO(long draw, long recordsTotal, List<ApplicationDTO> data) {
        super(draw, recordsTotal, data);
    }
}

