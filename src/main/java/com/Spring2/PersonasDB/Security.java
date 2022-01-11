
package com.Spring2.PersonasDB;

// Esta clase se crea como hermana del main

import com.Spring2.PersonasDB.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//Habilita la seguridad web
@EnableWebSecurity
//Permite agregar una serie de propiedades en la aplicacion. El prePostEnabled premite autorizar las url
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{
// Autenticacion y las autorizaciones
// UserDatailService -> loadByUserName -> UsuarioServicio
    @Autowired
    private UsuarioServicio usuarioServicio;
// Un metodo que va a configurar la autenticacion
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    }
// Configuracion de las peticiones http

    @Override
//    "/css/*","/img/*","/js/*"
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/*","/img/*","/js/*","/templates/*","/templates.fragments/*").permitAll()
                .and().formLogin()
                      .loginPage("/login")
                      .usernameParameter("username")
                      .passwordParameter("password")
                      .defaultSuccessUrl("/")
                      .loginProcessingUrl("/logincheck")
                      .failureUrl("/login?error=error")
                      .permitAll()
                .and().logout()
                      .logoutUrl("/logout")
                      .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
    }
    
}
