package es.arbox.eGestion.service.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import es.arbox.eGestion.entity.socios.Cuota;
import es.arbox.eGestion.entity.socios.Curso;
import es.arbox.eGestion.utils.Utilidades;

@Service
public class MailService {

	@Autowired
    private JavaMailSender mailSender;
	
	public void correoPagoSocio(Cuota cuota) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("atleticoalbaida@gmail.com");
        message.setTo(cuota.getSocioCurso().getSocio().getEmail()); 
        
        if(!StringUtils.isEmpty(cuota.getSocioCurso().getSocio().getEmail2())) {
        	String [] cc = {"atleticoalbaida@gmail.com", cuota.getSocioCurso().getSocio().getEmail2()};
        	message.setCc(cc);
        } else {
        	message.setCc("atleticoalbaida@gmail.com");
        }
        
        message.setSubject(String.format("[RECIBO CUOTA] %1$s %2$s: %3$s %4$s", 
        		cuota.getMes().getDescripcion(), 
        		cuota.getSocioCurso().getCurso().getDescripcion(),
        		cuota.getSocioCurso().getSocio().getNombre(),
        		cuota.getSocioCurso().getSocio().getApellidos())); 
        message.setText(String.format("Confirmación de pago realizado:\n\n"
        		+ "- Socio: %1$s %7$s \n"
        		+ "- Curso: %4$s \n"
        		+ "- Mes: %3$s \n"
        		+ "- Escuela: %5$s \n"
        		+ "- Categoría: %6$s \n"
        		+ "- Importe: %2$s € \n"
        		+ "- Fecha: %8$s",
        		cuota.getSocioCurso().getSocio().getNombre(),
        		cuota.getImporte(),
        		cuota.getMes().getDescripcion(),
        		cuota.getSocioCurso().getCurso().getDescripcion(),
        		cuota.getSocioCurso().getEscuela().getDescripcion(),
        		cuota.getSocioCurso().getCategoria().getDescripcion(),
        		cuota.getSocioCurso().getSocio().getApellidos(),
        		Utilidades.formatDateToString(cuota.getFecha() != null ? cuota.getFecha() : new Date())));
        
        mailSender.send(message);
	}
	
	public void recordatorio(Curso curso) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("atleticoalbaida@gmail.com");
        message.setTo("lacobrailio@gmail.com"); 
        
        message.setSubject(String.format("[RESUMEN DE CUOTA PENDIENTE] %1$s", 
        		curso.getId())); 
        message.setText(String.format("Prueba envío correo:\n\n"));
        
        mailSender.send(message);
	}
}
