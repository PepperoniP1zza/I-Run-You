package com.project.irunyou.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.irunyou.security.JwtAuthenticationFilter;
import com.project.irunyou.security.TokenProvider;

// 2023-01-25 홍지혜
// HTTP 요청 이후의 설정
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// WebSecurityConfigurerAdapter - deprecated 
	
	@Autowired private TokenProvider tokenProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf().disable()
				.cors().and()	// cors 활성화
				// cross site request forgery 사이트간 위조 요청 : 인증된 사용자의 토큰을 탈취해 위조된 요청을 보냈을 경우 파악해 방지
				// rest api에서는 권한이 필요한 요청을 위해서 인증 정보를 포함시켜야 한다. 서버에 인증정보를 저장하지 않기 때문에 필요 없음(JWT를 쿠키에 저장하지 않기 때문)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				//JWT를 사용하기 때문에 세션도 사용하지 않는다.
				.and()
				.formLogin().disable()	// HTTP Basic Authentication 사용안함
				.httpBasic().disable()	// Form Based Authentication 사용 안함
				.authorizeRequests()	
				.antMatchers("/auth/**","/v2/api-docs").permitAll() // 해당 요청에 관해서는 모두 접근 가능
				.anyRequest().authenticated()	// 그 외는 인증해야함
				.and()
				.addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
				// UsernamePasswordAuthenticationFilter 전에 JwtAuthenticationFilter를 실행
				.build();

	}	

}
