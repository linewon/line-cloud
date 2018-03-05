package line.consumer.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import line.consumer.hello.feign.client.HelloServiceFeignClient;

@Controller
public class HelloController {

	@Autowired
	private HelloServiceFeignClient helloServiceFeignClient;
	
	@GetMapping("/hello-from-other-side")
	@ResponseBody
	public String helloFromHelloService() {
		try {
			String resp = helloServiceFeignClient.hello();
			return resp;
		} catch (Exception e) {
			return "9999";
		}
	}
}
