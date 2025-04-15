package kr.co.iei;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import kr.co.iei.chat.model.service.ChatHandler;
@Configuration
@EnableWebSocket
public class WebConfig implements WebMvcConfigurer, WebSocketConfigurer{
  	@Autowired
	private ChatHandler chat;
	@Value("${file.root}")
	private String root;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chat, "chat").setAllowedOrigins("*");		
	}
	@Bean
	public BCryptPasswordEncoder bCrypt() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry
	.addResourceHandler("/profile/**")
	.addResourceLocations("file:///"+root+"/profile/");
	
	registry.addResourceHandler("/assets/event/thumb/**")
	.addResourceLocations("file:///"+root+"/event/thumb/");
	registry
	.addResourceHandler("/assets/place/image/**")
	.addResourceLocations("file:///"+root+"/place/image/");
	registry
	.addResourceHandler("/assets/plan/thumb/**")
	.addResourceLocations("file:///"+root+"/plan/thumb/");
	}
}

