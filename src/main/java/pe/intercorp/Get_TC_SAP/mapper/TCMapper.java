package pe.intercorp.Get_TC_SAP.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import pe.intercorp.Get_TC_SAP.entity.TableTC;

public class TCMapper implements RowMapper<TableTC> {

    @Override
    @Nullable
    public TableTC mapRow(ResultSet rs, int rowNum) throws SQLException {
        var result = new TableTC();
        result.setFecha(rs.getString("fecha"));
        return result;
    }

}
