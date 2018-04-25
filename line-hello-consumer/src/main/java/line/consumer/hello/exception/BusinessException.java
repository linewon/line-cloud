package line.consumer.hello.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends Exception {

	private static final long serialVersionUID = 5512332478582988279L;
	
	private String respCode;
	private String respInfo;
}
