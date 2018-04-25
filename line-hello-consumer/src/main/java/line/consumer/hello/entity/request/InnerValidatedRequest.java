package line.consumer.hello.entity.request;

import javax.validation.Valid;

import line.consumer.hello.entity.base.request.BaseUserRequest;
import line.consumer.hello.entity.request.data.InnerValidatedRequestData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InnerValidatedRequest extends BaseUserRequest {

	/**
	 * 现在这个东西，没有现成的嵌套验证。
	 * 需要每个对象都些个验证的注解。。。。
	 * 太麻烦了吧。。
	 */
	@Valid
	private InnerValidatedRequestData reqData;
}
