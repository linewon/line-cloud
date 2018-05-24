package line.zuul.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import line.zuul.gateway.filter.UserVerifyFilter;

@Configuration
public class FilterConfiguration {

	@Bean
	public UserVerifyFilter userVerifyFilter() {
		return new UserVerifyFilter();
	}
}
