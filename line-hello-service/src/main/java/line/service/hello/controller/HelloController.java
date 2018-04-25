package line.service.hello.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloService")
public class HelloController {

	@Value("${server.port}")
	private String port;
	
	@GetMapping("/hello")
	public String hello() {
		return "hello from producer1 : " + port;
	}
}
