package line.consumer.hello.entity.base.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseUserRequest extends BaseRequest {

	@NotBlank(message = "用户ID不能为空")
	private String userId;
}
