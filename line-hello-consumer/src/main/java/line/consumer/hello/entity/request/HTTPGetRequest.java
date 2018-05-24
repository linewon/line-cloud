package line.consumer.hello.entity.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HTTPGetRequest {

	@NotBlank(message = "姓名不能为空")
	@JsonProperty("userName")
	private String name;
	private String phone;
	@NotNull(message = "性别不能为空")
	private Boolean sex;
	@NotNull(message = "年龄不能为空")
	private Integer age;
}
