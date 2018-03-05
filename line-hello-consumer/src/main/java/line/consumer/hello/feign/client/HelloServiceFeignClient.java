package line.consumer.hello.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("line-hello-service")
public interface HelloServiceFeignClient {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String hello(); // 类中的方法和远程服务中contoller中的方法名和参数需保持一致。
}
