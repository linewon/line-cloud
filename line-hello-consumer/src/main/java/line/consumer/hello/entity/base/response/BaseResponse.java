package line.consumer.hello.entity.base.response;

import line.consumer.hello.commons.RespConstants;
import line.consumer.hello.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse {
	
	private String respCode;
	private String respInfo;
	
	public BaseResponse(BusinessException exception) {
		this.respCode = exception.getRespCode();
		this.respInfo = exception.getRespInfo();
	}
	
	public BaseResponse() {
		this.respCode = RespConstants.FAILED;
		this.respInfo = RespConstants.FAILED_INFO;
	}
	
	public static final BaseResponse SUCCESS = new BaseResponse(
												RespConstants.SUCCESS,
												RespConstants.SUCCESS_INFO);
}
