package es.arbox.eGestion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import es.arbox.eGestion.service.config.MenuService;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "es.arbox.eGestion"
})
@EnableWebSecurity
public class WebMvcConfig extends WebSecurityConfigurerAdapter
implements WebMvcConfigurer {
	
	@Autowired
	MenuService menuService;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/resources/**")
            .addResourceLocations("/resources/");
    }
	
	@Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000000);
        return multipartResolver;
    }
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.userDetailsService(usuariosAccesoService);
//    	/*
        auth.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder)
        .withUser("Albaida").password(passwordEncoder.encode("club")).roles("USER");
        /**/
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/resources/**");
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/**").hasRole("USER")
            .antMatchers("/resources/**", "/decorators/**").permitAll()
            .and().formLogin().loginPage("/login/").loginProcessingUrl("/login/logar2")//.defaultSuccessUrl("/gestion/gastos/")
            .and().logout().logoutSuccessUrl("/login/").permitAll()
            .and().csrf().disable();
    }
}
