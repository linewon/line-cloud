package line.consumer.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import line.consumer.hello.entity.request.InnerMultiTypesRequest;
import line.consumer.hello.entity.request.InnerValidatedRequest;
import line.consumer.hello.entity.request.RegistryEditorRequest;
import line.consumer.hello.properties.LoggingProperty;


/**
 * 
 * @author line
 *
 */
@RestController
@Validated
// what's this annotation for ?
@RequestMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
public class CommonController {
	
	@Autowired
	private LoggingProperty loggingProperty;

	/**
	 * test for @Validated & exceptionHandler
	 */
	@PostMapping("/registry/editor")
	public String registryEditor(@RequestBody @Validated RegistryEditorRequest request) throws Exception {
		
		String result = loggingProperty.getConfig() + loggingProperty.getPath();
		
		return result;
	}
	
	/**
	 * test for nested inner @Validated
	 */
	@PostMapping("/inner/validated")
	public String innerValidated(@RequestBody @Validated InnerValidatedRequest request) {
		
		
		return "GOT!";
	}
	
	/**
	 * test for nested inner @Validated
	 */
	@PostMapping("/inner/multi-types")
	public String innerMultiTypes(@RequestBody InnerMultiTypesRequest request) {
		
		
		return JSON.toJSONString(request);
	}
	
//	@PostMapping("/inner/inner-with-map")
//	public String innerWithMap(@ReuqestBody )
}
