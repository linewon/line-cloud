package line.consumer.hello.exception.handler;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import line.consumer.hello.commons.RespConstants;
import line.consumer.hello.entity.base.response.BaseDataResponse;
import line.consumer.hello.entity.base.response.BaseResponse;
import line.consumer.hello.entity.base.response.BaseResponseData;
import line.consumer.hello.entity.response.WrongInputParamsRespData;
import line.consumer.hello.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionProcessor {

	/**
	 * 是否应该转成String输出？ 会不会先被转成jsonObj然后再被转成JSONString？
	 */
	private String getRespStr(BaseResponse response) {
		String respJsonStr = JSON.toJSONString(response);
		String respClz = null; // 要在这里根据响应的Class类别来判断和哪种请求匹配的话，最好为每个请求定义一个单独的response类型
		try {
			respClz = ((BaseDataResponse) response).getRespData().getClass().getSimpleName();
		} catch (Exception e) {
			log.debug("父类试图获取子类数据", e);
		}
		if (respClz == null) {
			log.info("response:{}", respJsonStr);
		} else {
			log.info("response:{}, class:{}", respJsonStr, respClz);
		}
		return respJsonStr;
	}

	// 这两个有什么区别呢？
	// private <T extends BaseResponse> String getRespStr(T response) {}

	/**
	 * 处理正常业务逻辑中抛出的异常
	 */
	@ExceptionHandler
	@ResponseBody
	public String exceptionHandle(BusinessException e) {
		BaseResponse response = new BaseResponse(e);
		return getRespStr(response);
	}

	
//	MethodArgumentNotValidException
	
	/**
	 * 处理参数不正确导致的异常
	 */
	@ExceptionHandler
	@ResponseBody
	public String exceptionHandle(BindException e) {

		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		List<String> errMsgs = new ArrayList<>();
		for (ObjectError err : errors) {
			errMsgs.add(err.getDefaultMessage());
		}
		BaseResponseData respData = new WrongInputParamsRespData(errMsgs);
		BaseDataResponse response = new BaseDataResponse(RespConstants.WRONG_INPUT_PARAMS,
				RespConstants.WRONG_INPUT_PARAMS_INFO, respData);
		return getRespStr(response);
	}
	
//	@ExceptionHandler
//	@ResponseBody
//	public String ExceptionHandler()

	/**
	 * 处理系统未能捕捉到的异常
	 */
	@ExceptionHandler
	@ResponseBody
	public String exceptionHandle(Exception e) {
		log.error(RespConstants.FAILED_INFO, e); // 未正确捕捉的异常，在这里打印。
		BaseResponse response = new BaseResponse();
		return getRespStr(response);
	}
}
