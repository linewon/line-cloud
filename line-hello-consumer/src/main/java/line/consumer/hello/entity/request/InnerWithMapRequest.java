package line.consumer.hello.entity.request;

import java.util.Map;

import line.consumer.hello.entity.request.data.InnerValidatedRequestData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InnerWithMapRequest extends InnerValidatedRequestData {

	private Map<String, String> innerMap;
}
