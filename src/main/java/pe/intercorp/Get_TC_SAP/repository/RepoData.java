package pe.intercorp.Get_TC_SAP.repository;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.intercorp.Get_TC_SAP.entity.TableTC;
import pe.intercorp.Get_TC_SAP.mapper.TCMapper;

@Repository
public class RepoData {
        @Autowired
        private JdbcTemplate jdbcTemplate;

        public void insert(String destino, String fecha, String tipo_cambio) {
                String sql = """
                                    INSERT INTO EINTERFACE.IFH_TC_SAP_API
                                     (mnd_destino,fec_tip_cam, tip_cambio, tip_cotizacion, mnd_origen)
                                     VALUES
                                     (?,?,?,'M','PEN')
                                """;

                jdbcTemplate.update(sql, destino, fecha, tipo_cambio);
        }

        public List<TableTC> search(String fecha, String moneda) {
                List<TableTC> result = null;

                String query = """
                                SELECT TO_DATE(FEC_TIP_CAM,'YYYYMMDD') AS FECHA
                                FROM EINTERFACE.IFH_TC_SAP_API
                                WHERE RTRIM(FEC_TIP_CAM)=?
                                AND RTRIM(mnd_destino)=?
                                        """;

                result = jdbcTemplate.query(query,
                                new Object[] { fecha, moneda },
                                new int[] { Types.CHAR, Types.CHAR },
                                new TCMapper());

                return result;
        }

        public List<TableTC> search2() {
                List<TableTC> result = null;

                String query = """
                                SELECT TO_DATE(FEC_TIP_CAM,'YYYYMMDD') AS fecha
                                FROM EINTERFACE.IFH_TC_SAP_API
                                WHERE DOWNLOAD_DATE IS NULL
                                        """;
                result = jdbcTemplate.query(query,
                                new Object[] {},
                                new int[] {},
                                new TCMapper());

                return result;
        }

        public void process() {
                SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("SP_TIPO_CAMBIO_SAP");
                simpleJdbcCall.execute();
        }

}