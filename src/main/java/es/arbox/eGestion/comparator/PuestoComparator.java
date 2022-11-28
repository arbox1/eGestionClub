package es.arbox.eGestion.comparator;

import java.util.Comparator;

import es.arbox.eGestion.dto.PuestoDTO;

public class PuestoComparator implements Comparator<PuestoDTO> {

	@Override
	 public int compare(PuestoDTO o1, PuestoDTO o2) {
    	int res = 0;
    	res = o1.getPuntos() - o2.getPuntos();
    	if(res == 0) {
    		if ((o1.getTantosFavor() - o1.getTantosContra()) > (o2.getTantosFavor() - o2.getTantosContra())) {
    			res = 1;	            			
    		} else if ((o1.getTantosFavor() - o1.getTantosContra()) < (o2.getTantosFavor() - o2.getTantosContra())) {
    			res = -1;
    		} else {
    			o1.getEquipo().compareToIgnoreCase(o1.getEquipo());
    		}
    	}
    	
        return (-1)*res;
    }

}
