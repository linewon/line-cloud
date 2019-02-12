package line.consumer.hello.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import line.consumer.hello.entity.request.HTTPGetRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试GET请求和@RequestBody注解怎么配合使用
 * 
 * @author line
 *
 */
@RestController
@Slf4j
public class HTTPGetController {

	@PostMapping(value = "/get-request")
	public String httpPostWithRequestBody(@RequestBody @Validated HTTPGetRequest request) {
		
		log.info("REQ: {}", JSON.toJSONString(request));

		return JSON.toJSONString(request);
	}
	
	@PostMapping(value = "/get-request-with-valid", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String httpPostWithRequestValid(@RequestBody @Validated HTTPGetRequest request, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors().get(0).getDefaultMessage();
		}
		log.info("REQ: {}", JSON.toJSONString(request));

		return JSON.toJSONString(request);
	}
	
	@GetMapping(value = "/get-request", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String httpGetWithRequestMapping(@Validated HTTPGetRequest request) {
		
		log.info("REQ: {}", JSON.toJSONString(request));

		return JSON.toJSONString(request);
	}
	
	@PostMapping(value = "/post-request")
	public String httpPostWithRequestUrl(HTTPGetRequest request) {
		
		log.info("REQ: {}", JSON.toJSONString(request));

		return JSON.toJSONString(request);
	}
	
	@PostMapping(value = "/post-request-with-map")
	public String httpPostWithRequestUrl(ModelMap request) {
		
		log.info("REQ: {}", JSON.toJSONString(request));

		return JSON.toJSONString(request);
	}
	

	@PostMapping(value = "/post-request-with-string")
	public String httpPostWithRequestUrl(@RequestBody String request) {
		
		log.info("REQ: {}", JSON.toJSONString(request));

		return JSON.toJSONString(request);
	}
}
