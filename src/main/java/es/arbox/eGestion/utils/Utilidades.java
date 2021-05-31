package es.arbox.eGestion.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

	public static String formatDateToString (Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}
}
