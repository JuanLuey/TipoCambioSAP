package pe.intercorp.Get_TC_SAP.entity;
import java.util.List;

import lombok.Data;

@Data
public class TipoCambio {
    public String E_TPCAMBIO;
    public List<TipoCambioDet> E_TIPO_CAMBIO;
}
