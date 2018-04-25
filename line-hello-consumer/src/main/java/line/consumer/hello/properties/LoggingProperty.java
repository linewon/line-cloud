package line.consumer.hello.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "logging")
@Getter
@Setter
public class LoggingProperty {
	private String config;
	private String path;
}
