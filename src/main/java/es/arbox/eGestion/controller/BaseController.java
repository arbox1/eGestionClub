package es.arbox.eGestion.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.Errores;
import es.arbox.eGestion.entity.config.MenuEstructura;
import es.arbox.eGestion.service.config.MenuService;

@Controller
@RequestMapping("/base")
public class BaseController {
	
	@Autowired
	private MenuService menuService;
	
	@ExceptionHandler(Exception.class)
    public String genericErrorPage(Model model, HttpServletRequest req, Exception e) {
		Errores error = new Errores(e.getMessage());
		
		menuService.guardar(error);
		
		model.addAttribute("error", error.getId());
			
        return "/error";
    }
	
	@InitBinder
	public void bigDecimalCustomBinder(WebDataBinder bind) {
		final DecimalFormat FORMATTER = (DecimalFormat) NumberFormat.getNumberInstance(Locale.FRANCE);

		// Allow the HTML field to be empty without generating any exception
		boolean allowEmptyValue = true;

		// Creation of a new binder for the type "BigDecimal"
		CustomNumberEditor binder = new CustomNumberEditor(Double.class, new NumberFormat() {

			private static final long serialVersionUID = 1L;

			@Override
			public Number parse(String source, ParsePosition parsePosition) {
				return FORMATTER.parse(source, parsePosition);
			}

			@Override
			public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
				return FORMATTER.format(number, toAppendTo, pos);
			}

			@Override
			public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
				return FORMATTER.format(number, toAppendTo, pos);
			}
		}, allowEmptyValue);

		// Registration of the binder 
		bind.registerCustomEditor(Double.class, binder);
	}
	
	@InitBinder
	public void dateCustomBinder(WebDataBinder bind) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    bind.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
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
