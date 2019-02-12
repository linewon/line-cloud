package line.service.hello.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/helloService")
@Slf4j
public class HelloController {

	@Value("${server.port}")
	private String port;
	
	@GetMapping("/hello")
	public String hello() {
		log.info("hello from producer: {}", port);
		return "hello from producer: " + port;
	}
}
