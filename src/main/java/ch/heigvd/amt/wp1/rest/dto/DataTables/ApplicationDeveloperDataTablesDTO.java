package ch.heigvd.amt.wp1.rest.dto.DataTables;

import ch.heigvd.amt.wp1.rest.dto.ApplicationDeveloperDTO;

import java.util.List;

public class ApplicationDeveloperDataTablesDTO extends DataTablesDTO<ApplicationDeveloperDTO>{
    public ApplicationDeveloperDataTablesDTO(long draw, long recordsTotal, List<ApplicationDeveloperDTO> data) {
        super(draw, recordsTotal, data);
    }
}
