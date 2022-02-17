package es.arbox.eGestion.config;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class PdfReportView extends AbstractView {

    private static final String CONTENT_TYPE = "application/pdf";

    private String templatePath;
    private String exportFileName;

    public PdfReportView(String templatePath, String exportFileName) {
        this.templatePath = templatePath;
        if (exportFileName != null) {
            try {
                exportFileName = URLEncoder.encode(exportFileName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        this.exportFileName = exportFileName;
        setContentType(CONTENT_TYPE);
    }

    protected Map<String, Object> getParamsMap(Map<String, Object> map) throws Exception {
        Map<String, Object> params = new HashMap<>();
        for (String key : map.keySet()) {
            Object val = map.get(key);
            if (val instanceof JRDataSource) {
                continue;
            } else {
                params.put(key, val);
            }
        }
        return params;
    }

    protected JRDataSource getDataSource(Map<String, Object> map) throws Exception {
        for (String key : map.keySet()) {
            Object val = map.get(key);
            if (val instanceof JRDataSource) {
                return (JRDataSource) map.get(key);
            }
        }
        return new JREmptyDataSource();
    }
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> map,
                                           HttpServletRequest request, HttpServletResponse response)
            throws Exception {
 
        response.setCharacterEncoding("utf-8");
        response.setContentType(getContentType());
        //response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + exportFileName + ".pdf");
        
        try (OutputStream ouputStream = response.getOutputStream()) {
                         JasperReport jasperReport = JasperCompileManager.compileReport (templatePath); // Compilar jrxml
                         JasperPrint jasperPrint = JasperFillManager.fillReport (jasperReport, getParamsMap (map), getDataSource (map)); // Procesamiento y carga de datos
            JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
 
 
    }

}
