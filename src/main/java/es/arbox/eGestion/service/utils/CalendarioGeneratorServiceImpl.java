package es.arbox.eGestion.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.arbox.eGestion.entity.ligas.CalendarioLiga;
import es.arbox.eGestion.entity.ligas.Equipo;
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.ligas.Jornada;
import es.arbox.eGestion.entity.ligas.Resultado;

@Service
public class CalendarioGeneratorServiceImpl implements CalendarioGeneratorService {

	//Algoritmo: http://www.delphiaccess.com/forum/c-c-49/fixture-todos-contra-todos-en-c/
	
	private int[] equipos;
	private Integer[][] matriz1,matriz2;
	
	//Num de jornadas = (N-1)*2, con N = num equipos. (N-1) es una vuelta.
	
	/**
	 * @param N -> numero de equipos
	 */
	public List<Grupo> calendario(List<Grupo> grupos){
		
		for(Grupo grupo : grupos) {
			int N = grupo.getEquipos().size();
			Map<Integer, Equipo> mEquipos = new HashMap<>();
			equipos = new int[N];
			int indice = 0;
			//Asigno posiciones del array a los equipos
			for(Equipo equipo : grupo.getEquipos()) {
				mEquipos.put(indice, equipo);
				equipos[indice] = indice;
				indice++;
			}
			
			matriz1 = new Integer[N-1][N/2];
			matriz2 = new Integer[N-1][N/2];
			
			//Relleno las matrices
			/*   Matriz 1    	 Matriz 2			 
				1   2   3		6   5   4
				4   5   1		6   3   2
				2   3   4		6   1   5
				5   1   2		6   4   3
				3   4   5		6   2   1
				
				Resultado:
				
				J1	6vs1	2vs5	3vs4
				J2	4vs6	5vs3	1vs2
				J3	6vs2	3vs1	4vs5
				J4	5vs6	1vs4	2vs3
				J5	6vs3	4vs2	5vs1
			 */
			
			int cont = 0;
			int cont2 = N-3;
			
			Map<Integer, Jornada> mJornadas = new HashMap<>();
			for(int i=0;i<N-1;i++){
				Jornada jornada = new Jornada();
				jornada.setGrupo(grupo);
				jornada.setNumero(i+1);
				Map<Integer, CalendarioLiga> mCalendarios = new HashMap<>();
				for(int j=0;j<N/2;j++){
					CalendarioLiga calendario = new CalendarioLiga();
					calendario.setJornada(jornada);
					//matriz1
					matriz1[i][j] = equipos[cont];
					
					cont++;
					if(cont==(N-1)) cont=0;
					
					//matriz2
					if(j==0) matriz2[i][j] = N-1;
					else {
						matriz2[i][j] = equipos[cont2];
						cont2--;
						if(cont2==-1) cont2 = N-3;
					}
					
					//Elaboro la matriz final de enfrentamientos por jornada (primera vuelta)
					if(j==0){
						if(i%2==0) {
							calendario.setEquipoa(mEquipos.get(matriz2[i][j]));
							calendario.setEquipob(mEquipos.get(matriz1[i][j]));
						}
						else {
							calendario.setEquipoa(mEquipos.get(matriz1[i][j]));
							calendario.setEquipob(mEquipos.get(matriz2[i][j]));
						}
					}
					else { 
						calendario.setEquipoa(mEquipos.get(matriz1[i][j]));
						calendario.setEquipob(mEquipos.get(matriz2[i][j]));
					}
					
					
					//segunda vuelta - al reves que la primera
					if("S".equals(grupo.getLiga().getIdaVuelta())) {
						if(j==0){
							if (i%2==0) {
								calendario.setEquipoa(mEquipos.get(matriz1[i][j]));
								calendario.setEquipob(mEquipos.get(matriz2[i][j]));
							}
							else {
								calendario.setEquipoa(mEquipos.get(matriz2[i][j]));
								calendario.setEquipob(mEquipos.get(matriz1[i][j]));
							}
						}
						else {
							calendario.setEquipoa(mEquipos.get(matriz2[i][j]));
							calendario.setEquipob(mEquipos.get(matriz1[i][j]));
						}
					}
					
					Resultado resultado = new Resultado();
					resultado.setCalendario(calendario);
					List<Resultado> resultados = new ArrayList<>();
					resultados.add(resultado);
					calendario.setResultados(resultados);
					
					mCalendarios.put(j, calendario);
				}
				
				List<CalendarioLiga> lCalendarios = new ArrayList<>();
				for(Integer ii : mCalendarios.keySet()) {
					lCalendarios.add(mCalendarios.get(ii));
				}
				jornada.setCalendarios(lCalendarios);
				
				mJornadas.put(i, jornada);
			}
			
			List<Jornada> lJornadas = new ArrayList<>();
			for(Integer ii : mJornadas.keySet()) {
				lJornadas.add(mJornadas.get(ii));
			}
			grupo.setJornadas(lJornadas);
		}
		
		return grupos;
	}
}
