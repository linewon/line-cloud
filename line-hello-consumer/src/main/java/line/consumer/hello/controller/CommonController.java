package line.consumer.hello.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import line.consumer.hello.entity.base.response.BaseResponse;
import line.consumer.hello.entity.request.RegistryEditorRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CommonController {

	@PostMapping("/registry/editor")
	public BaseResponse registryEditor(@RequestBody @Validated RegistryEditorRequest request) {

		
		return null;
	}
}
