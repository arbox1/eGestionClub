package es.arbox.eGestion.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.apiclub.captcha.Captcha;
import es.arbox.eGestion.config.PdfReportView;
import es.arbox.eGestion.dto.RespuestaAjax;
import es.arbox.eGestion.dto.ValoresDTO;
import es.arbox.eGestion.entity.Errores;
import es.arbox.eGestion.entity.config.MenuEstructura;
import es.arbox.eGestion.entity.config.Usuario;
import es.arbox.eGestion.entity.documento.Documento;
import es.arbox.eGestion.service.config.MenuService;
import es.arbox.eGestion.utils.CaptchaUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/base")
public class BaseController {
	
	@Autowired
	public MenuService menuService;
	
	@ExceptionHandler(Exception.class)
    public String genericErrorPage(Model model, HttpServletRequest req, Exception e) {
		Errores error = new Errores(e.getMessage()); e.printStackTrace();
		
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
		
		Usuario usuario = getUsuarioLogado();
		if(usuario != null && valores != null && valores.getId().equals(1)) {
			List<MenuEstructura> lMenuEstructura = menuService.getMenuEstructura(valores.getId(), usuario.getId());
	
			result.setResultado("menuEstructura", MenuEstructura.getListaMapa(lMenuEstructura));
			result.setResultado("ok", "S");
		} else if (valores != null && valores.getId().equals(2)) {
			List<MenuEstructura> lMenuEstructura = menuService.getMenuEstructura(valores.getId());
			
			result.setResultado("menuEstructura", MenuEstructura.getListaMapa(lMenuEstructura));
			result.setResultado("ok", "S2");
		}
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
	}
	
	@ResponseBody
	@PostMapping(value = "/usuario", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String usuario(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		RespuestaAjax result = new RespuestaAjax();
		
		result.setResultado("usuario", getUsuarioLogado());
		result.setResultado("ok", "S");
		
		ObjectMapper Obj = new ObjectMapper();
		return Obj.writeValueAsString(result);
	}
	
	@PostMapping("/documento")
	public HttpEntity<byte[]> getDocumento(@ModelAttribute("valor") ValoresDTO valores) throws IOException {
      Documento documento = menuService.obtenerPorId(Documento.class, valores.getId());
      
      MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
      header.add("Content-disposition", "attachment; name=filex; filename=" + documento.getNombre());
      header.add("Content-type", documento.getMime());
      
      return new HttpEntity<byte[]>(documento.getFichero(), header);
	}
	
	@PostMapping(value = "/captcha", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String captcha(@RequestBody ValoresDTO valores) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		RespuestaAjax result = new RespuestaAjax();
		
		getCaptcha(valores);
		result.setResultado("valor", valores.getMapa());
		result.setResultado("ok", "S");
		
		return menuService.serializa(result);
    }
	
	protected void getCaptcha(ValoresDTO valores) {
		Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
		valores.setHiddenCaptcha(captcha.getAnswer());
		valores.setCaptcha(""); // value entered by the User
		valores.setRealCaptcha("data:realCaptcha/jpg;base64," + CaptchaUtil.encodeCaptcha(captcha));
		
	}
	
	public ModelAndView getInforme(String informe, String nombre, List<?> datos, Map<String, Object> mapa) throws IOException {
      
		JRDataSource dataSource = new JRBeanCollectionDataSource(datos);
		mapa.put("datasource", dataSource);
		File file = null;
		try {
			 file  = ResourceUtils.getFile("classpath:"+informe+".jrxml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView(new PdfReportView(file.getPath(), nombre), mapa);
		return mv ;
	}
	
	protected Usuario getUsuarioLogado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario usuario = null;
		if (principal instanceof Usuario) {
			usuario = (Usuario) principal;
		}
		
		return usuario;
	}
}
