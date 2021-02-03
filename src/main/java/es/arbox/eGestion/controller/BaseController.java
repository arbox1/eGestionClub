package es.arbox.eGestion.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.config.MenuEstructura;
import es.arbox.eGestion.service.config.MenuService;

@Controller
@RequestMapping("/base")
public class BaseController {
	
	@Autowired
	private MenuService menuService;
	
	@ResponseBody
	@PostMapping(value = "/menu", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getSearchResultViaAjax(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		List<MenuEstructura> lMenuEstructura = menuService.getMenuEstructura(valores.getId());

		result.setResultado("menuEstructura", MenuEstructura.getListaMapa(lMenuEstructura));
		result.setResultado("ok", "S");
		
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
	}
}
