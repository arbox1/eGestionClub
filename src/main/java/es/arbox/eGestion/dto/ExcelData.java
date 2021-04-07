package es.arbox.eGestion.dto;

import java.util.List;

public class ExcelData {
	private List<String> cabecera;
	private List<List<String>> valores;
	public List<String> getCabecera() {
		return cabecera;
	}
	public void setCabecera(List<String> cabecera) {
		this.cabecera = cabecera;
	}
	public List<List<String>> getValores() {
		return valores;
	}
	public void setValores(List<List<String>> valores) {
		this.valores = valores;
	}
}
