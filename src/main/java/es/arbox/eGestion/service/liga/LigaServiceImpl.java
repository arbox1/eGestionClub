package es.arbox.eGestion.service.liga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.arbox.eGestion.comparator.PuestoComparator;
import es.arbox.eGestion.dao.liga.LigaDAO;
import es.arbox.eGestion.dto.PuestoDTO;
import es.arbox.eGestion.entity.ligas.CalendarioLiga;
import es.arbox.eGestion.entity.ligas.Equipo;
import es.arbox.eGestion.entity.ligas.Grupo;
import es.arbox.eGestion.entity.ligas.Jornada;
import es.arbox.eGestion.entity.ligas.Liga;
import es.arbox.eGestion.entity.ligas.Resultado;
import es.arbox.eGestion.service.config.GenericServiceImpl;

@Service
public class LigaServiceImpl extends GenericServiceImpl implements LigaService {
	
	@Autowired
	private LigaDAO ligaDAO;

	@Override
	@Transactional
	public List<Liga> getLigasFiltro(Liga liga){
		return ligaDAO.getLigasFiltro(liga);
	}

	@Override
	@Transactional
	public List<Grupo> getDatosLiga(Liga liga) {
		List<Grupo> grupos = ligaDAO.getGrupos(liga);
		liga = super.obtenerPorId(Liga.class, liga.getId());
		
		for(Grupo grupo : grupos) {
			List<Equipo> equipos = super.obtenerTodosFiltroOrden(Equipo.class, String.format(" grupo.id = %1$s ", grupo.getId()), " id " );
			
			/*Cargamos los datos de la clasificación*/
			Map<Integer, PuestoDTO> puestos = new HashMap<>();
			for(Equipo equipo : equipos) {
				PuestoDTO puesto = new PuestoDTO();
				puesto.setIdEquipo(equipo.getId());
				puesto.setEquipo(equipo.getDescripcion());
				puestos.put(equipo.getId(), puesto);
			}
			
			
			List<Jornada> jornadas = ligaDAO.getJornadas(grupo);
			
			for(Jornada jornada : jornadas) {
				List<CalendarioLiga> calendarios = ligaDAO.getCalendarios(jornada);
				
				for(CalendarioLiga calendario : calendarios) {
					List<Resultado> resultados = ligaDAO.getResultados(calendario);
					
					calendario.setResultados(resultados);
					
					/* Actualizamos la clasificacion */
					boolean tieneResultado = false;
					Integer ganaA = 0;
					Integer ganaB = 0;
					Integer tantosA = 0;
					Integer tantosB = 0;
					for(Resultado resultado : resultados) {
						if(resultado.getResultadoa() != null && resultado.getResultadob() != null) {
							ganaA += resultado.getResultadoa() - resultado.getResultadob() > 0 ? 1 : 0;
							ganaB += resultado.getResultadob() - resultado.getResultadoa() > 0 ? 1 : 0;
							tantosA += resultado.getResultadoa();
							tantosB += resultado.getResultadob();
							tieneResultado = true;
						}
					}
					
					if(tieneResultado) {
						PuestoDTO puestoA = puestos.get(calendario.getEquipoa().getId());
						PuestoDTO puestoB = puestos.get(calendario.getEquipob().getId());
						if(ganaA > ganaB) { // GANA A
							puestoA.setPuntos(puestoA.getPuntos() + liga.getPuntosPartidoGanado());
							puestoA.setEncuentrosGanados(puestoA.getEncuentrosGanados()+1);
							if("S".equals(calendario.getNoPresentadob())) {
								puestoB.setEncuentrosNoPresentados(puestoB.getEncuentrosNoPresentados()+1);
								puestoB.setPuntos(puestoB.getPuntos() + liga.getPuntosPartidoNoPresentado());
							} else {
								puestoB.setEncuentrosPerdidos(puestoB.getEncuentrosPerdidos()+1);
								puestoB.setPuntos(puestoB.getPuntos() + liga.getPuntosPartidoPerdido());
							}
						} else if (ganaA < ganaB) { //GANA B
							puestoB.setPuntos(puestoB.getPuntos() + liga.getPuntosPartidoGanado());
							puestoB.setEncuentrosGanados(puestoB.getEncuentrosGanados()+1);
							if("S".equals(calendario.getNoPresentadoa())) {
								puestoA.setEncuentrosNoPresentados(puestoA.getEncuentrosNoPresentados()+1);
								puestoA.setPuntos(puestoA.getPuntos() + liga.getPuntosPartidoNoPresentado());
							} else {
								puestoA.setEncuentrosPerdidos(puestoA.getEncuentrosPerdidos()+1);
								puestoA.setPuntos(puestoA.getPuntos() + liga.getPuntosPartidoPerdido());
							}
						} else { // EMPATA
							if("S".equals(calendario.getNoPresentadoa()) && "S".equals(calendario.getNoPresentadob())) {
								puestoA.setPuntos(puestoA.getPuntos() + liga.getPuntosPartidoNoPresentado());
								puestoB.setPuntos(puestoB.getPuntos() + liga.getPuntosPartidoNoPresentado());
								puestoB.setEncuentrosNoPresentados(puestoB.getEncuentrosNoPresentados()+1);
								puestoB.setEncuentrosNoPresentados(puestoB.getEncuentrosNoPresentados()+1);
							} else {
								puestoA.setPuntos(puestoA.getPuntos() + liga.getPuntosPartidoEmpatado());
								puestoB.setPuntos(puestoB.getPuntos() + liga.getPuntosPartidoEmpatado());
								puestoB.setEncuentrosNoPresentados(puestoB.getEncuentrosNoPresentados()+1);
								puestoB.setEncuentrosNoPresentados(puestoB.getEncuentrosNoPresentados()+1);
							}
						}
						puestoA.setEncuentrosJugados(puestoA.getEncuentrosJugados()+1);
						puestoB.setEncuentrosJugados(puestoB.getEncuentrosJugados()+1);
						puestoA.setTantosFavor(puestoA.getTantosFavor()+tantosA);
						puestoA.setTantosContra(puestoA.getTantosContra()+tantosB);
						puestoB.setTantosFavor(puestoB.getTantosFavor()+tantosB);
						puestoB.setTantosContra(puestoB.getTantosContra()+tantosA);
					}
				}
				
				jornada.setCalendarios(calendarios);
			}
			
			grupo.setJornadas(jornadas);
			
			List<PuestoDTO> lPuestos = new ArrayList<>();
			for(Integer key : puestos.keySet()) {
				lPuestos.add(puestos.get(key));
			}
			lPuestos.sort(new PuestoComparator());
			
			Integer i = 1;
			for(PuestoDTO puesto : lPuestos) {
				puesto.setPosicion(i);
				i++;
			}
			
			grupo.setPuestos(lPuestos);
		}
		return grupos;
	}
	
	@Override
	@Transactional
	public CalendarioLiga getCalendarioLiga(CalendarioLiga calendario) {
		List<Resultado> resultados = ligaDAO.getResultados(calendario);
		CalendarioLiga res = new CalendarioLiga();
		if(resultados != null && resultados.size() > 0) {
			res = resultados.get(0).getCalendario();
			res.setResultados(resultados);
		}
		
		return res;
	}
	
	@Override
	@Transactional
	public List<Resultado> getResultadosEquipo(Equipo equipo) {
		CalendarioLiga calendario = new CalendarioLiga();
		calendario.setEquipoa(equipo);
		
		List<Resultado> resultados = ligaDAO.getResultados(calendario);
		
		for(Resultado resultado : resultados) {
			if(resultado.getResultadoa() != null && resultado.getResultadob() != null){
				if(resultado.getCalendario().getEquipoa().getId().equals(equipo.getId())) {
					if(resultado.getResultadoa()>resultado.getResultadob()) {
						resultado.setClase("green");
					} else if(resultado.getResultadoa()<resultado.getResultadob()) {
						resultado.setClase("red");
					}
				} else if(resultado.getCalendario().getEquipob().getId().equals(equipo.getId())) {
					if(resultado.getResultadoa()>resultado.getResultadob()) {
						resultado.setClase("red");
					} else if(resultado.getResultadoa()<resultado.getResultadob()) {
						resultado.setClase("green");
					}
				}
			}
		}
		
		return ligaDAO.getResultados(calendario);
	}
}
