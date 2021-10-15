package es.arbox.eGestion.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class MySiteMeshFilter extends ConfigurableSiteMeshFilter {

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/login/", "/WEB-INF/views/decorators/decoratorBase.jsp")
			   .addDecoratorPath("/principal/", "/WEB-INF/views/decorators/decorator.jsp")
			   .addDecoratorPath("/error/", "/WEB-INF/views/decorators/decorator.jsp")
			   .addDecoratorPath("/socios/*", "/WEB-INF/views/decorators/decorator.jsp")
			   .addDecoratorPath("/mantenimiento/*", "/WEB-INF/views/decorators/decorator.jsp")
			   .addDecoratorPath("/economica/*", "/WEB-INF/views/decorators/decorator.jsp")
			   .addDecoratorPath("/comunicacion/*", "/WEB-INF/views/decorators/decorator.jsp");
	}

}
