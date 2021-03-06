package es.arbox.eGestion.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseEntidad {
	
	public static <T> List<Map<String, Object>> getListaMapa(List<T> datos){
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(T dato : datos) {
			if (dato instanceof BaseEntidad) {
				try {
					result.add(((BaseEntidad)dato).getMapa());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public Map<String, Object> getMapa() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException{
		Map<String, Object> result = new HashMap<>();
		for(Field field : this.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if(field.get(this) instanceof BaseEntidad) {
				Method m = field.getType().getMethod("getMapa");
				result.put(field.getName(), m.invoke(field.get(this), new Object[]{}));
			} else if(field.get(this) instanceof List) {
				List<?> c = (List<?>)field.get(this);
				List<Map<String, Object>> lTemp = new ArrayList<>();
				for(Object obj : c) {
					Map<String, Object> temp = new HashMap<>();
					for(Field fielInterna : obj.getClass().getDeclaredFields()) {
						fielInterna.setAccessible(true);
						if(fielInterna.get(obj).getClass() != this.getClass()) {
							if(fielInterna.get(obj) instanceof BaseEntidad) {
								Method m = fielInterna.getType().getMethod("getMapa");
								temp.put(fielInterna.getName(), m.invoke(fielInterna.get(obj), new Object[]{}));
							} else {
								temp.put(fielInterna.getName(), fielInterna.get(obj));
							}
						}
					}
					lTemp.add(temp);
				}
				
				result.put(field.getName(), lTemp);
			} else {
				result.put(field.getName(), field.get(this));
			}
		}
		
		return result;
	}
}
