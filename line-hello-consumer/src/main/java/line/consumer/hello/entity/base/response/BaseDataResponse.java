package line.consumer.hello.entity.base.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDataResponse extends BaseResponse {

	private BaseResponseData respData;
	
	public BaseDataResponse(String respCode, String respInfo, BaseResponseData respData) {
		super(respCode, respInfo);
		this.respData = respData;
	}
}
