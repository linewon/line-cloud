package line.consumer.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import line.consumer.hello.feign.client.HelloServiceFeignClient;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HelloController {

	@Autowired
	private HelloServiceFeignClient helloServiceFeignClient;
	
	@GetMapping("/hello-from-other-side")
	@ResponseBody
	public String helloFromHelloService() {
		// 使用 slf4j + logback + lombok 的日志系统
		log.info("this is the LOG_TEXT!");
		try {
			String resp = helloServiceFeignClient.hello();
			return resp;
		} catch (Exception e) {
			return "9999";
		}
	}
}
