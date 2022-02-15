package es.arbox.eGestion.config;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import es.arbox.eGestion.entity.config.MenuRol;
import es.arbox.eGestion.service.config.MenuService;
import es.arbox.eGestion.utils.Utilidades;

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
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/resources/**")
            .addResourceLocations("/resources/");
    }
	
	@Bean(name = "javaMailSender")
	public JavaMailSender javaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    
	    mailSender.setUsername("atleticoalbaida.padel@gmail.com");
	    mailSender.setPassword("principefelipe");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    
	    return mailSender;
	}
	
	@Bean
    public SimpleMailMessage emailTemplate()
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("lacobrailio@gmail.com");
        message.setFrom("lacobrailio@gmail.com");
        message.setText("Prueba Email ERROR");
        
        return message;
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
	public ViewResolver beanNameViewResolver() {
		BeanNameViewResolver resolver = new BeanNameViewResolver();
		return resolver;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new PasswordEncoder() {
	        @Override
	        public String encode(CharSequence charSequence) {
	            return Utilidades.getMd5(charSequence.toString());
	        }

	        @Override
	        public boolean matches(CharSequence charSequence, String s) {
	            return Utilidades.getMd5(charSequence.toString()).equals(s);
	        }
	    };
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder)
        .withUser("Albaida").password(passwordEncoder.encode("club")).roles("USER");
        
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    
//    @Override
	public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/resources/**");
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	List<MenuRol> lMenuRol = menuService.obtenerTodos(MenuRol.class);
    	
    	for(MenuRol menuRol : lMenuRol) {
    		http.authorizeRequests()
    			.antMatchers("/" + menuRol.getMenu().getPagina() + "**").hasAuthority(menuRol.getRol().getCodigo());
    	}
    	
        http.authorizeRequests()
        	.antMatchers("/principal/**").authenticated()
            .antMatchers("/resources/**", "/decorators/**").permitAll()
            .and().formLogin()
            		.loginPage("/login/")
            		.loginProcessingUrl("/login/logar2")
            		.defaultSuccessUrl("/principal/", true)
            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("login/logout", "GET"))
            .deleteCookies("remove").logoutSuccessUrl("/login/").permitAll()
            .and().csrf().disable();
    }
}
