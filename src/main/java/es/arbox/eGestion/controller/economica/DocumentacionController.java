package es.arbox.eGestion.controller.economica;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.arbox.eGestion.controller.BaseController;
import es.arbox.eGestion.dto.Mensajes;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.entity.economica.Pago;
import es.arbox.eGestion.enums.FamiliasDocumento;
import es.arbox.eGestion.enums.TiposMensaje;
import es.arbox.eGestion.service.economica.DocumentoService;

@Controller
@RequestMapping("/economica/documentacion")
public class DocumentacionController extends BaseController {

	@Autowired
	protected DocumentoService documentosService;
	
	@GetMapping("/")
	public String listaDocumentos(Model model, @ModelAttribute("buscador") Documento documento) {
		model.addAttribute("nuevo", new Documento());
		model.addAttribute("documentos", new ArrayList<Documento>());
		model.addAttribute("tipos", documentosService.getTiposDocumento(FamiliasDocumento.DOCUMENTACION));
		model.addAttribute("buscador", documento == null ? new Documento() : documento);
		return "/economica/documentacion";
	}
	
	@PostMapping("/buscar")
    public String buscar(Model model, @ModelAttribute("buscador") Documento documento, RedirectAttributes redirectAttrs) {
		
		model.addAttribute("nuevo", new Pago());
		model.addAttribute("documentos", documentosService.getBusqueda(documento));
		model.addAttribute("tipos", documentosService.getTiposDocumento(FamiliasDocumento.DOCUMENTACION));
		model.addAttribute("buscador", documento);
		
		return "/economica/documentacion";
    }
	
	@PostMapping(value = "/guardar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax guardar(@ModelAttribute Documento documento, RedirectAttributes redirectAttrs) throws IllegalArgumentException, IllegalAccessException {
		RespuestaAjax result = new RespuestaAjax();
		
		String opcion = documento.getId() != null ? "actualizado" : "subido";
		
		documentosService.guardar(documento, getUsuarioLogado());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, String.format("Documento %1$s correctamente.", opcion));
		result.setMensajes(mensajes.getMensajes());
		
        return result;
    }
	
	@PostMapping(value = "/eliminar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody RespuestaAjax  eliminar(@ModelAttribute Documento documento, RedirectAttributes redirectAttrs) {
		RespuestaAjax result = new RespuestaAjax();
		documentosService.eliminar(documento.getClass(), documento.getId());
		
		Mensajes mensajes = new Mensajes();
		mensajes.mensaje(TiposMensaje.success, "Documento eliminado correctamente.");
		result.setMensajes(mensajes.getMensajes());
		
		return result;
    }
}
