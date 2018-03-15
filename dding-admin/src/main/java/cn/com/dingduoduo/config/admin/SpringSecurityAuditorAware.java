package cn.com.dingduoduo.config.admin;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();

		String userId = null;
		if (principal != null && principal instanceof UserDetails) {
			userId = ((UserDetails) principal).getUsername();
		} else if (principal != null) {
			userId = principal.toString();
		}
		return userId;
	}

}