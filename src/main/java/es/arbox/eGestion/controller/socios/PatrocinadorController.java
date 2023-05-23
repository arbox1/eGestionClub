package es.arbox.eGestion.controller.socios;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.arbox.eGestion.controller.mantenimiento.MantenimientoController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.socios.Patrocinador;
import es.arbox.eGestion.enums.TiposMensaje;

@Controller
@RequestMapping("/socios/patrocinador")
public class PatrocinadorController extends MantenimientoController<Patrocinador>{
	
	@Override
	public String getReturn() {
		return "/socios/patrocinador";
	}

	@Override
	public Patrocinador getDato() {
		return new Patrocinador();
	}

	@Override
	public Map<String, Object> addModel() {
		return null;
	}
	
	@PostMapping("/guardar")
	@Override
    public String guardar(@ModelAttribute("nuevo") Patrocinador t, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		String msg = t.getId() != null ? "actualizado" : "creado";
		
		if(t.getId() != null) {
			Patrocinador p = menuService.obtenerPorId(Patrocinador.class, t.getId());
			
			t.setLogo(p.getLogo());
			t.setNombreLogo(p.getNombreLogo());
			t.setMime(p.getMime());
		}
		
		menuService.guardar(t);
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Dato %1$s correctamente.", msg));
		redirectAttrs.addFlashAttribute("mensajes", mensajes.getMensajes());
		
		return String.format("redirect:%1$s/", getReturn());
    }
	
	@PostMapping(value = "/guardarDocumento", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardarDocumento(@ModelAttribute Patrocinador patrocinador, RedirectAttributes redirectAttrs) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		Patrocinador p = menuService.obtenerPorId(Patrocinador.class, patrocinador.getId());
		p.setLogo(patrocinador.getLogo());
		p.setMime(patrocinador.getMime());
		p.setNombreLogo(patrocinador.getNombreLogo());
		
		menuService.guardar(p, getUsuarioLogado());
		
		result.setResultado("ok", "S");
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Logo almacenado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping("/logo")
	public HttpEntity<byte[]> getLogo(@ModelAttribute("valor") ValoresDTO valores) throws IOException {
		Patrocinador patrocinador = menuService.obtenerPorId(Patrocinador.class, valores.getId());
      
		MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
		header.add("Content-disposition", "attachment; name=filex; filename=" + patrocinador.getNombreLogo());
		header.add("Content-type", patrocinador.getMime());
      
		return new HttpEntity<byte[]>(patrocinador.getLogo(), header);
	}
}
