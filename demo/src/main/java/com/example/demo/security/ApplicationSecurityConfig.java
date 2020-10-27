package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.demo.student.StudentController;

@Configuration //config olarak ayarladığımız sınıfın başına yazılır
@EnableWebSecurity //security springi aktif hale getirmek için yazdık
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	
	// BCRYPT password encoder aktif hale getiriliyor //MAKİNG ACTİVE PASSWORD ENCODING WITH BCRYPT
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) { //PasswordEncoder oluşturduğumuz ApplicationSecurityConfig classından gelmekte
		this.passwordEncoder = passwordEncoder;
	}
	//----
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		//client'ın açmak istediği sayfaya erişim izni burdan kontrol edilir ve izin verilince controllera geçiş yapar ve istediği sayfa gösterilir
		//FORM BASED AUTHENTICATION
		http
			.csrf().disable() // put delete post requestlere izin vermek için şimdilik csrf'yi kapalı tutuyoruz
			.authorizeRequests()
				.antMatchers("/", "/index", "/css/*", "js/*") //belirtilen sayfalara request eden
					.permitAll() //herkes olabilir
				.antMatchers("/api/**") 
					.hasRole(ApplicationUserRoleEnum.STUDENT.name()) // http://localhost:8080/api/.... ULR'sine sadece Role STUDENT (olarak auhenticated(login) ) olanlara erişim izni var 
				.antMatchers(HttpMethod.DELETE, "/management/api/**") //belirtilen urly'ye delete req yapan, 
					.hasAuthority(ApplicationUserPermissionEnum.COURSE_WRITE.getPermission()) //authenticated user'in permission'u "course:write" ise izin verilir //COURSE_WRITE.getPermission() çıktısı :: "course:write"
				.antMatchers(HttpMethod.POST, "/management/api/**") //belirtilen urly'ye post req yapan,
					.hasAuthority(ApplicationUserPermissionEnum.COURSE_WRITE.getPermission())  //authenticated user'in permission'u "course:write" ise izin verilir
				.antMatchers(HttpMethod.PUT, "/management/api/**") //belirtilen urly'ye put req yapan, 
					.hasAuthority(ApplicationUserPermissionEnum.COURSE_WRITE.getPermission()) //authenticated user'in permission'u "course:write" ise izin verilir
				.antMatchers(HttpMethod.GET, "/management/api/**") //belirtilen urlye get yapan,  
					.hasAnyRole(ApplicationUserRoleEnum.ADMIN.name(), ApplicationUserRoleEnum.ADMINTRAINEE.name()) //role'ler sadece admin ve ADMINTRAINEE olabilir //ADMIN.name() çıktısı:: "ADMIN" //ADMINTRAINEE.name() çıktısı:: "ADMINTRAINEE"
				.and()
			.authorizeRequests()
				.anyRequest() //geri kalan herhangi bir request bir sayafaya erişmek için
					.authenticated()//authenticated(LOGİN) olmalı  //any request must be authenticated 
				.and()
			.httpBasic(); //authenticated mekanizmasını basic auth olarak ayarlıyoruz //we setting mechanism to basic auth  //BASIC AUTH
	}	
	
	
	// IN MEMORY USER DETAILS MANAGER
	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		
		UserDetails annaSmithUser = User.builder() //manual olarak user oluşturuyoruz username password authorities belirterek
				.username("annasmith") 
				.password(passwordEncoder.encode("password"))
				//.roles(ApplicationUserRoleEnum.STUDENT.name())	//ROLES İLE kullanıcıya role(authority) ATIYORUZ
				.authorities(ApplicationUserRoleEnum.STUDENT.getGrantedAuthoritiesSet()) //authority ayarlamak için Set<SimpleGrantedAuthority> arrayi koyuyoruz parametreye
				.build();
		//debug'da annaSmithUser çıktısı:::: org.springframework.security.core.userdetails.User@37aa9563: Username: annasmith; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_STUDENT
		
		
		UserDetails lindaUser = User.builder() //manual olarak user oluşturuyoruz username password authorities belirterek
				.username("linda")
				.password(passwordEncoder.encode("password123"))
				//.roles(ApplicationUserRoleEnum.ADMIN.name())	
				.authorities(ApplicationUserRoleEnum.ADMIN.getGrantedAuthoritiesSet())
				.build();
		//debug'da lindaUser çıktısı:::: org.springframework.security.core.userdetails.User@6234ece: Username: linda; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_ADMIN,course:read,course:write,student:read,student:write

		
		UserDetails tomUser = User.builder()  //manual olarak user oluşturuyoruz username password authorities belirterek
				.username("tom")
				.password(passwordEncoder.encode("password123"))
				//.roles(ApplicationUserRoleEnum.ADMINTRAINEE.name())	
				.authorities(ApplicationUserRoleEnum.ADMINTRAINEE.getGrantedAuthoritiesSet())
				.build();
		//debug'da tomUser çıktısı::: org.springframework.security.core.userdetails.User@1c152: Username: tom; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_ADMINTRAINEE,course:read,student:read
		 
		return new InMemoryUserDetailsManager(annaSmithUser, lindaUser, tomUser);
	}	
		
		

	
	
	
}
