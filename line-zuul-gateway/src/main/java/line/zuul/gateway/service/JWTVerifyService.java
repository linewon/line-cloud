package line.zuul.gateway.service;

import org.springframework.stereotype.Service;

@Service
public class JWTVerifyService {
	
	public boolean verify(String jwt) {
		if (jwt == null)
			return false;
		return true;
	}
}
