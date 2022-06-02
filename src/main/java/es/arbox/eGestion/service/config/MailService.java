package es.arbox.eGestion.service.config;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import es.arbox.eGestion.entity.actividades.DocumentoActividad;
import es.arbox.eGestion.entity.actividades.Participante;
import es.arbox.eGestion.entity.socios.Cuota;
import es.arbox.eGestion.entity.socios.Meses;
import es.arbox.eGestion.entity.socios.SociosCurso;
import es.arbox.eGestion.service.socios.CuotaService;
import es.arbox.eGestion.service.socios.SociosCursoService;
import es.arbox.eGestion.utils.Utilidades;

@Service
public class MailService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	SociosCursoService sociosCursoService;
	
	@Autowired
	CuotaService cuotaService;
	
	public void correoNotificacionParticipante(Participante participante) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("atleticoalbaida@gmail.com");
        message.setTo(participante.getEmail()); 
        
    	message.setCc("atleticoalbaida@gmail.com");
        
        message.setSubject(String.format("[INSCRIPCION ACTIVIDAD] %1$s de la modalidad %2$s", 
        		participante.getActividad().getDescripcion(), 
        		participante.getActividad().getTipo().getDescripcion())); 
        message.setText(String.format("Cambio en su solicitud:\n\n"
        		+ "- Nombre: %1$s \n"
        		+ "- DNI: %2$s \n"
        		+ "- Nº Participantes: %3$s \n"
        		+ "- Pagado: %9$s \n"
        		+ "- Importe: %10$s € \n"
        		+ "- Teléfono: %4$s \n"
        		+ "- Email: %5$s \n"
        		+ "- Observaciones: %6$s \n"
        		+ "- Estado: %7$s \n"
        		+ "- Fecha: %8$s \n"
        		+ "- Permitir publicaciones en redes sociales: %11$s \n",
        		participante.getNombre(),
        		participante.getDni(),
        		participante.getCantidad(),
        		participante.getTelefono(),
        		participante.getEmail(),
        		participante.getObservacion(),
        		participante.getEstado().getDescripcion(),
        		Utilidades.formatDateToString(participante.getFecha() != null ? participante.getFecha() : new Date()),
        		participante.getPagado().isEmpty() || participante.getPagado() == "N" ? "No" : "Si",
        		participante.getImporte(),
        		participante.getLopd().isEmpty() || participante.getLopd() == "N" ? "No" : "Si"
        		));
        
        mailSender.send(message);
	}
	
	public void correoInscripcionParticipante(Participante participante, String password) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("atleticoalbaida@gmail.com");
        message.setTo(participante.getEmail()); 
        
    	message.setCc("atleticoalbaida@gmail.com");
        
        message.setSubject(String.format("[INSCRIPCION ACTIVIDAD] %1$s de la modalidad %2$s", 
        		participante.getActividad().getDescripcion(), 
        		participante.getActividad().getTipo().getDescripcion())); 
        message.setText(String.format("Confirmación de inscripcion:\n\n"
        		+ "- Nombre: %1$s \n"
        		+ "- DNI: %2$s \n"
        		+ "- Nº Participantes: %3$s \n"
        		+ "- Teléfono: %4$s \n"
        		+ "- Email: %5$s \n"
        		+ "- Observaciones: %6$s \n"
        		+ "- Estado: %7$s \n"
        		+ "- Fecha: %8$s \n"
        		+ "- Permitir publicaciones en redes sociales: %10$s\n\n"
        		+ "Datos de acceso para consultar Inscripción \n\n"
        		+ "- Usuario: %5$s \n"
        		+ "- Contraseña: %9$s",
        		participante.getNombre(),
        		participante.getDni(),
        		participante.getCantidad(),
        		participante.getTelefono(),
        		participante.getEmail(),
        		participante.getObservacion(),
        		participante.getEstado().getDescripcion(),
        		Utilidades.formatDateToString(participante.getFecha() != null ? participante.getFecha() : new Date()),
        		password,
        		participante.getLopd().isEmpty() || participante.getLopd() == "N" ? "No" : "Si"
        		));
        
        mailSender.send(message);
	}
	
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
	
	public void recordatorio(SociosCurso buscador) {
        List<Meses> meses = cuotaService.obtenerTodosOrden(Meses.class, " m_numero "); 
        
        GregorianCalendar calendar = new GregorianCalendar();
        Meses mesActual = cuotaService.obtenerPorId(Meses.class, calendar.get(Calendar.MONTH)+1);
        
        for(SociosCurso socioCurso : sociosCursoService.obtenerSociosFiltro(buscador.getCurso().getId(), buscador.getEscuela().getId(), buscador.getCategoria().getId())) {
    		boolean pendiente = false;
    		SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom("atleticoalbaida@gmail.com");
            
            message.setTo(socioCurso.getSocio().getEmail());
            if(!StringUtils.isEmpty(socioCurso.getSocio().getEmail2())) {
            	String [] cc = {socioCurso.getSocio().getEmail2()};
            	message.setCc(cc);
            }
    	
        	StringBuilder cuerpo = new StringBuilder();
        	
        	message.setSubject(String.format("[RESUMEN DE CUOTAS PENDIENTE] %1$s", 
            		socioCurso.getSocio().getNombreCompleto()));
        	
        	cuerpo.append(String.format("Desde el Club Atletico Albaida le recordamos las mensualidades que están pendiente de pago por el socio %1$s hasta la fecha actual: \n\n", socioCurso.getSocio().getNombreCompleto()));
        	
        	Meses mesInicial = cuotaService.obtenerPorId(Meses.class, 9);
        	if(socioCurso.getEntrada() != null && socioCurso.getEntrada().getOrden() > mesInicial.getOrden()) {
        		mesInicial = socioCurso.getEntrada();
        	}
        	
        	Meses mesFinal = cuotaService.obtenerPorId(Meses.class, 5);
        	if(socioCurso.getSalida() != null && socioCurso.getSalida().getOrden() < mesFinal.getOrden()) {
        		mesFinal = socioCurso.getSalida();
        	}
        	
        	if(mesActual.getOrden() < mesFinal.getOrden())
        		mesFinal = mesActual;
        	
        	List<Cuota> cuotas = cuotaService.getCuotas(socioCurso.getId());
        	
        	boolean matricula = false;
        	Meses mesMatricula = cuotaService.obtenerPorId(Meses.class, 13);
			Iterator<Cuota> it = cuotas.iterator();
			while(it.hasNext() && !matricula) {
				Cuota cuota = (Cuota)it.next();
				if(cuota.getMes().getId() == mesMatricula.getId()) {
					matricula = true;
				}
			}
			
			if(!matricula) {
				pendiente = true;
				cuerpo.append(String.format("\t\t - %1$s", mesMatricula.getDescripcion()));
			}
				
        	
        	for(Meses mes : meses) {
        		if(mes.getOrden() >= mesInicial.getOrden() && mes.getOrden() <= mesFinal.getOrden()) {
        			boolean encontrado = false;
        			it = cuotas.iterator();
        			while(it.hasNext() && !encontrado) {
        				Cuota cuota = (Cuota)it.next();
        				if(cuota.getMes().getId() == mes.getId()) {
        					encontrado = true;
        				}
        			}
        			
        			if(!encontrado) {
        				pendiente = true;
        				cuerpo.append(String.format("\t\t - %1$s", mes.getDescripcion()));
        			}
        		}
        	}
        	
        	if(!pendiente)
        		cuerpo = new StringBuilder(String.format("Desde el Club Atletico Albaida le recordamos que el socio %1$s esta al corriente de pagos hasta la fecha actual: \n\n", socioCurso.getSocio().getNombreCompleto()));
        	
        	message.setText(cuerpo.toString());
        	mailSender.send(message);
        }
	}
	
	public void correoActividadSalida(Participante participante, List<DocumentoActividad> documentos) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        message.setFrom("atleticoalbaida@gmail.com");
        message.setTo(participante.getEmail()); 
        
//        message.setCc("atleticoalbaida@gmail.com");
        message.setSubject(String.format("[Realización Actividad] %1$s del próximo %2$s", 
        		participante.getActividad().getDescripcion(),
        		Utilidades.formatDateToString(participante.getActividad().getFechaInicio())));
        
        StringBuilder cuerpo = new StringBuilder(String.format("Desde el Club Atletico Albaida le queremos notificar que la persona %10$s figura inscrita para la próxima actividad de \"%1$s\". \n\n"
        		+ "\t\t- Lugar de salida: %9$s \n"
        		+ "\t\t- Fecha y Hora de inicio: %2$s %3$s \n"
        		+ "\t\t- Fecha y Hora de fin: %4$s %5$s \n"
        		+ "\t\t- Participantes: %6$s \n"
        		+ "\t\t- Importe: %7$s \n"
        		+ "\t\t- Pagado: %8$s",
        		participante.getActividad().getDescripcion(),
        		Utilidades.formatDateToString(participante.getActividad().getFechaInicio()),
        		Utilidades.formatDateToStringHora(participante.getActividad().getFechaInicio()),
        		Utilidades.formatDateToString(participante.getActividad().getFechaFin()),
        		Utilidades.formatDateToStringHora(participante.getActividad().getFechaFin()),
        		participante.getCantidad(),
        		participante.getImporte(),
        		"S".equals(participante.getPagado()) ? "Si" : "No",
        		participante.getActividad().getLugarSalida(),
        		participante.getNombre()));
        
        if(!StringUtils.isEmpty(participante.getActividad().getContenido())) {
        	cuerpo.append(String.format("\n\n Desde el Club queremos hacer constar las siguientes indicaciones: \n\n"
        			+ "\t\t %1$s", participante.getActividad().getContenido()));
        }
        
        if(documentos != null && documentos.size() > 0) {
        	cuerpo.append("\n\n Se adjunta documentación relacionada con la actividad que esperamos sea de vuestro interés. \n\n");
        }
        
        cuerpo.append("\n\n Atentamente un cordial saludo. Club Atletico Albaida");
        
        message.setText(cuerpo.toString());
       
        for(DocumentoActividad documentoActividad : documentos) {
        	final InputStreamSource attachment = new ByteArrayResource(documentoActividad.getDocumento().getFichero());
        	message.addAttachment(documentoActividad.getDocumento().getNombre(), attachment);
        }
        
        mailSender.send(mimeMessage);
	}
	
	public void correoActividadLlegada(Participante participante, List<DocumentoActividad> documentos) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        message.setFrom("atleticoalbaida@gmail.com");
        message.setTo(participante.getEmail()); 
        
//        message.setCc("atleticoalbaida@gmail.com");
        message.setSubject(String.format("[Finalización Actividad] %1$s del pasado %2$s", 
        		participante.getActividad().getDescripcion(),
        		Utilidades.formatDateToString(participante.getActividad().getFechaInicio())));
        
        StringBuilder cuerpo = new StringBuilder(String.format("Tras la realización de la actividad %1$s realizada el pasado día %2$s el Club Atletico Albaida quiere darle las gracias por participar en la misma. \n\n"
        		+ "Del mismo modo le anima a seguir participando con nosotros en cuantas actividades realiciemos. \n",
        		participante.getActividad().getDescripcion(),
        		Utilidades.formatDateToString(participante.getActividad().getFechaInicio())));
        
        if(documentos != null && documentos.size() > 0) {
        	cuerpo.append("\n\n Se adjunta documentación relacionada con la actividad que esperamos sea de vuestro interés. \n\n");
        }
        
        cuerpo.append("\n\n Atentamente un cordial saludo. Club Atletico Albaida");
        
        message.setText(cuerpo.toString());
       
        for(DocumentoActividad documentoActividad : documentos) {
        	final InputStreamSource attachment = new ByteArrayResource(documentoActividad.getDocumento().getFichero());
        	message.addAttachment(documentoActividad.getDocumento().getNombre(), attachment);
        }
        
        mailSender.send(mimeMessage);
	}
}
