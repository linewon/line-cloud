package line.consumer.hello.feign.hystrix;

import org.springframework.stereotype.Component;

import line.consumer.hello.feign.client.HelloServiceFeignClient;

@Component
public class HelloServiceHystrix implements HelloServiceFeignClient{

	@Override
	public String hello() {
		
		return "DEAD!";
	}
}
