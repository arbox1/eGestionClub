package es.arbox.eGestion.comparator;

import java.util.Comparator;

import es.arbox.eGestion.entity.ligas.Equipo;

public class RandomEquipoComparator implements Comparator<Equipo> {

	@Override
	public int compare(Equipo e1, Equipo e2) {
		int numero = (int)(Math.random()*10+1);
		return numero % 2 == 0 ? 1 : -1;
	}

}
