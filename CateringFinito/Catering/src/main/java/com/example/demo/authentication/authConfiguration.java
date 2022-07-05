package com.example.demo.authentication;
import javax.sql.DataSource;

import static com.example.demo.model.Credenziali.ruoloAdmin;
//import static com.example.demo.model.Credenziali.ruoloDefault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

	/**
	 * The AuthConfiguration is a Spring Security Configuration.
	 * It extends WebSecurityConfigurerAdapter, meaning that it provides the settings for Web security.
	 */
	@Configuration
	@EnableWebSecurity
	public class authConfiguration extends WebSecurityConfigurerAdapter {

	    /**
	     * The datasource is automatically injected into the AuthConfiguration (using its getters and setters)
	     * and it is used to access the DB to get the Credentials to perform authentiation and authorization
	     */
	    @Autowired
	    DataSource datasource;//SAREBBE IL DB

	    /**
	     * This method provides the whole authentication and authorization configuration to use.
	     */
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                // authorization paragraph: qui definiamo chi può accedere a cosa
	                .authorizeRequests()
	                // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini(con ** si intende tutto il resto)
	                .antMatchers(HttpMethod.GET, "/", "/index", "/login", "/buttonRegistrazione", "/css/**", "/images/**", "/failure").permitAll()
	                // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
	                .antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
	                // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
	                .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ruoloAdmin)
	                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ruoloAdmin)
	                // tutti gli utenti autenticati possono accere alle pagine rimanenti 
	                .anyRequest().authenticated()

	                // login paragraph: qui definiamo come è gestita l'autenticazione
	                // usiamo il protocollo formlogin 
	                .and().formLogin()
	                // la pagina di login si trova a /login
	                // NOTA: Spring gestisce il post di login automaticamente
	                .loginPage("/login")
	                // se il login ha successo, si viene rediretti al path /default
	                .defaultSuccessUrl("/default")

	                .failureUrl("/failure")
	                
	                // logout paragraph: qui definiamo il logout
	                .and().logout()
	                // il logout è attivato con una richiesta GET a "/logout"
	                .logoutUrl("/logout")
	                // in caso di successo, si viene reindirizzati alla /index page
	                .logoutSuccessUrl("/index")        
	                .invalidateHttpSession(true)
	                .clearAuthentication(true).permitAll();
	    }

	    /**
	     * DOVE MEMORIZZIAMO LE CREDENZIALI E COME FACCIAMO AD ACCEDERE ALLE CREDENZIALI
	     */
	    @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.jdbcAuthentication()//AUTENTICAZIONE SU DATABASE
	                //use the autowired datasource to access the saved credentials
	                .dataSource(this.datasource)
	                //retrieve username and role
	                .authoritiesByUsernameQuery("SELECT username, ruolo FROM Credenziali WHERE username=?")
	                //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
	                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM Credenziali WHERE username=?");
	    }

	    /**
	     * This method defines a "passwordEncoder" Bean.
	     * The passwordEncoder Bean is used to encrypt and decrpyt the Credentials passwords.
	     */
	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	}