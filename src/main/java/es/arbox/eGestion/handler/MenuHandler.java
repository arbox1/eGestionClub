package es.arbox.eGestion.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import es.arbox.eGestion.service.config.MenuService;

@Component
public class MenuHandler extends HandlerInterceptorAdapter {

	public MenuHandler(MenuService menuService) {
//		this.menuService = menuService;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

//		List<MenuEstructura> menuEstructura = menuService.getMenuEstructura(1);
//		modelAndView.addObject("menuEstructura", menuEstructura);
	}
}