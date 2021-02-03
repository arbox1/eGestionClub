package es.arbox.eGestion.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespuestaAjax {
	
	private Map<String, Object> resultados = new HashMap<String, Object>();
	
	private List<Mensaje> mensajes = new ArrayList<Mensaje>();
	
    private boolean validated = true;
	
	String code = "200";
	
    private String msg = "success";
    
	public void setResultado(String key, Object value) {
    	this.resultados.put(key, value);
    }
	public Map<String, Object> getResultados() {
		return resultados;
	}
	public void setResultados(Map<String, Object> resultados) {
		this.resultados = resultados;
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Mensaje> getMensajes() {
		return mensajes;
	}
	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
}