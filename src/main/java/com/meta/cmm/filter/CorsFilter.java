package com.meta.cmm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

@WebFilter(urlPatterns = "*.do")
public class CorsFilter implements Filter {

	//private final List<String> allowedOrigins = Arrays.asList("http://localhost:9700");
	@Value("${Globals.Allow.Origin}")
	private String[] allowedOrigins;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {
		
		System.out.println();

		//HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;

		// Access-Control-Allow-Origin
		//String origin = request.getHeader("Origin");
		
		//HTTP parameter directly written to HTTP header 
		String originHeader = allowedOrigins[0]+","+allowedOrigins[1]+","+allowedOrigins[2];
		System.out.println(originHeader);

		if (isValidOriginHeader(originHeader)) {
			// Security - Potential HTTP Response Splitting 분할응답 조치
			originHeader = originHeader
					.replace("\r", "")
					.replace("\n", "");
		}

		setCorsHeaders(response, originHeader);

		chain.doFilter(req, res);
	}
	
	private static boolean isValidOriginHeader(String originHeader) {
		return originHeader != null && !originHeader.isEmpty();
	}
	
	private static void setCorsHeaders(HttpServletResponse response, String originHeader) {
		response.setHeader("Access-Control-Allow-Origin", originHeader);

		// Access-Control-Max-Age
		response.setHeader("Access-Control-Max-Age", "3600");

		// Access-Control-Allow-Credentials
		response.setHeader("Access-Control-Allow-Credentials", "true");

		// Access-Control-Allow-Methods
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PATCH, PUT, DELETE");

		// Access-Control-Allow-Headers
		response.setHeader("Access-Control-Allow-Headers",
			"Origin, X-Requested-With, Content-Type, Accept, Authorization, " + "X-CSRF-TOKEN");
	}

	@Override
	public void init(FilterConfig filterConfig) {}

	@Override
	public void destroy() {}

}