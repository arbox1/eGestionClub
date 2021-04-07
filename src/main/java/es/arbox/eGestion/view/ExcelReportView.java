package es.arbox.eGestion.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import es.arbox.eGestion.dto.ExcelData;

public class ExcelReportView extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment;filename=\"export.xls\"");
		 ExcelData excel = (ExcelData) model.get("data");
		 Sheet sheet = workbook.createSheet("Export Data");
		 Row header = sheet.createRow(0);
		 int i = 0;
		 for(String cabecera : excel.getCabecera()) {
			 header.createCell(i++).setCellValue(cabecera);
		 }
		  
		 i = 1;
		 for(List<String> fila : excel.getValores()){
			 Row row = sheet.createRow(i++);
			 int j = 0;
			 for(String valor : fila) {
				 row.createCell(j++).setCellValue(valor);
			 }
		 }
	}

}
