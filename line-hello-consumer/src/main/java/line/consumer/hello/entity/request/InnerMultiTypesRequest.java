package line.consumer.hello.entity.request;

import java.util.List;
import java.util.Map;

import line.consumer.hello.entity.request.data.InnerValidatedRequestData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InnerMultiTypesRequest {

	private InnerValidatedRequestData body;
	
	private Map<String, Object> header;
	
	private List<Object> list;
}
