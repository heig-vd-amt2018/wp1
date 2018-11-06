package ch.heigvd.amt.wp1.rest.dto.DataTables;

import ch.heigvd.amt.wp1.rest.dto.UserDTO;

import java.util.List;

public class UserDataTablesDTO extends DataTablesDTO<UserDTO> {
    public UserDataTablesDTO(long draw, long recordsTotal, List<UserDTO> data) {
        super(draw, recordsTotal, data);
    }
}
