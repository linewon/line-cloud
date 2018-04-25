package line.consumer.hello.entity.request.data;

import org.hibernate.validator.constraints.NotBlank;

import line.consumer.hello.entity.base.request.BaseRequestData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InnerValidatedRequestData implements BaseRequestData {

	@NotBlank(message = "小区名称不能为空")
	private String houseName;
	@NotBlank(message = "房屋地址不能为空")
	private String houseAddress;
}
