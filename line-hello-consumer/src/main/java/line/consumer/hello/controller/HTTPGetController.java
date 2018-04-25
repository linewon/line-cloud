package line.consumer.hello.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import line.consumer.hello.entity.request.HTTPGetRequest;

/**
 * 测试GET请求和@RequestBody注解怎么配合使用
 * 
 * @author line
 *
 */
@RestController
public class HTTPGetController {

	@GetMapping(value = "/get-request", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String httpGetWithRequestBody(@Validated HTTPGetRequest request) {

		return JSON.toJSONString(request);
	}
}
