package line.consumer.hello.entity.request;

import org.hibernate.validator.constraints.NotBlank;

import line.consumer.hello.entity.base.request.BaseUserRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistryEditorRequest extends BaseUserRequest {

	@NotBlank(message = "姓名不能为空！")
	private String name;
	@NotBlank(message = "电话号码不能为空！")
	private String phoneNum;
}
