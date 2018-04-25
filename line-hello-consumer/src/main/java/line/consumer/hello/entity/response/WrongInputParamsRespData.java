package line.consumer.hello.entity.response;

import java.util.List;

import line.consumer.hello.entity.base.response.BaseResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class WrongInputParamsRespData implements BaseResponseData {

	@Getter
	@Setter
	private List<String> errMsgs;
}
