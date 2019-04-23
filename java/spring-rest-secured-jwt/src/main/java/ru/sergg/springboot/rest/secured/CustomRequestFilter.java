package ru.sergg.springboot.rest.secured;

//import com.amdocs.digital.ms.resource.inventory.business.internationalization.interfaces.IMessages;
//import com.amdocs.digital.ms.resource.inventory.resources.models.ErrorResponse;
//import com.codahale.metrics.MetricRegistry;
//import com.codahale.metrics.Timer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

//import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomRequestFilter extends OncePerRequestFilter {
	private static final int UNDEFINED_HTTP_STATUS = 999;
	private static final String LOG_SUCCESS_INFO = "ResourceLogSuccessInfo";
	private static final String LOG_FAILURE_INFO = "ResourceLogFailureInfo";
	private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";
	private static final String METRIC_NAME_PREFIX = "resource-inventory-resource";
		
	// Injects framework metric registry for metrics reporting
//	@Autowired
//    @Qualifier("dropwizardRegistry")
//    private MetricRegistry registry;

	// Injects request mapping bean to obtain the swagger generated API method info
	@Autowired
	private RequestMappingHandlerMapping requestMapping;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
	{
		String metricBaseName = getMetricBaseName(request);
		HttpServletResponseCopier responseCopier = createResponseCopier(response);
//		Timer.Context localTimer = null;
		
//		try {
//			registry.meter(metricBaseName + "_meter").mark();
//			localTimer = registry.timer(metricBaseName + "_timer").time();
//		} catch (Exception e) {
//			// Noop
//		}
        logger.info("start of request: " + metricBaseName);
		try {
			filterChain.doFilter(request, responseCopier);
		}
		catch (Exception ex) {
			throw ex;
		} finally {
			try {
				int status = getHttpStatus(responseCopier);
				String statusMeterName = getStatusMeterName(metricBaseName, status);
                logger.info("end of request: " + statusMeterName);
//				registry.meter(statusMeterName).mark();
//
//				long elapsedTimeNano = 0;
//				if (localTimer != null) {
//					elapsedTimeNano = localTimer.stop();
//				}

//				if (isSuccessHttpStatus(status)) {
//					registry.meter(getStatusMeterName(metricBaseName, "success")).mark();
//					if (logger.isInfoEnabled()) {
//						logger.info(messages.getMessage(LOG_SUCCESS_INFO,
//								request.getRequestURI(),
//								String.valueOf(status),
//								String.valueOf(TimeUnit.NANOSECONDS.toMillis(elapsedTimeNano))));
//
//					}
//				} else {
//					registry.meter(getStatusMeterName(metricBaseName, "failure")).mark();
//					if (responseCopier != null) {
//						responseCopier.flushBuffer();
//						String body = new String(responseCopier.getCopy(), getResponseCharacterEncoding(response));
//						String errorCode = getErrorResponseCode(body);
//						registry.meter(getStatusMeterName(metricBaseName, errorCode)).mark();
//
//						if (logger.isInfoEnabled()) {
//							logger.info(messages.getMessage(LOG_FAILURE_INFO,
//									request.getRequestURI(),
//									String.valueOf(status),
//									String.valueOf(TimeUnit.NANOSECONDS.toMillis(elapsedTimeNano)),
//									body));
//						}
//					}
//				}
			} catch (Exception ex) {
				// Noop
			}
		}
	}

	private String getResponseCharacterEncoding(HttpServletResponse response) {
		if (response != null) {
			return response.getCharacterEncoding();
		} else {
			return DEFAULT_CHARACTER_ENCODING;
		}
	}

	/**
	 * Wrap the original HttpServletResponse instance with a HttpServletResponseCopier instance
	 * so that we can log the response content on error.
	 */
	private HttpServletResponseCopier createResponseCopier(HttpServletResponse response) {
		try {
			if (response != null && response.getCharacterEncoding() == null) {
				response.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
			}
			return new HttpServletResponseCopier((HttpServletResponse) response);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Builds metric base name from the swagger generated API method mapped to the input request.
	 */
	private String getMetricBaseName(HttpServletRequest request) {
		try {
			HandlerExecutionChain hec = requestMapping.getHandler(request);
			HandlerMethod handlerMethod = (HandlerMethod) hec.getHandler();
			Method method = handlerMethod.getMethod();
			return String.format("%s.%s.%s", METRIC_NAME_PREFIX, method.getDeclaringClass().getSimpleName(), method.getName());
//			return MetricRegistry.name(String.format("%s.%s.%s", METRIC_NAME_PREFIX, method.getDeclaringClass().getSimpleName(), method.getName()));
		} catch (Exception e) {
			return METRIC_NAME_PREFIX;
		}
	}
	
	private String getStatusMeterName(String metricBaseName, int status) {
		return getStatusMeterName(metricBaseName, String.valueOf(status));
	}
	
	private String getStatusMeterName(String metricBaseName, String code) {
		return String.format("%s_%s_meter", metricBaseName, code);
	}

//	private String getErrorResponseCode(String body) {
//		try {
//			ErrorResponse errResponse = new ObjectMapper().readValue(body, ErrorResponse.class);
//			return errResponse.getCode();
//		} catch (Exception e) {
//			return "";
//		}
//	}

	private int getHttpStatus(HttpServletResponse response) {
		try {
			if (response != null) {
				return response.getStatus();
			} else {
				return UNDEFINED_HTTP_STATUS;
			}
		} catch (Exception ex) {
			return UNDEFINED_HTTP_STATUS;
		}
	}
	
	private boolean isSuccessHttpStatus(int status) {
		return (status/100 == 2);
	}
	
	// Response content copier classes for logging
	private static class ServletOutputStreamCopier extends ServletOutputStream {

	    private OutputStream outputStream;
	    private ByteArrayOutputStream copy;

	    public ServletOutputStreamCopier(OutputStream outputStream) {
	        this.outputStream = outputStream;
	        this.copy = new ByteArrayOutputStream(1024);
	    }

	    @Override
	    public void write(int b) throws IOException {
	        outputStream.write(b);
	        copy.write(b);
	    }

	    public byte[] getCopy() {
	        return copy.toByteArray();
	    }

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener arg0) {
			// Noop
		}
	}
	
	private static class HttpServletResponseCopier extends HttpServletResponseWrapper {

	    private ServletOutputStream outputStream;
	    private PrintWriter writer;
	    private ServletOutputStreamCopier copier;

	    public HttpServletResponseCopier(HttpServletResponse response) throws IOException {
	        super(response);
	    }

	    @Override
	    public ServletOutputStream getOutputStream() throws IOException {
	        if (writer != null) {
	            throw new IllegalStateException("getWriter() has already been called on this response.");
	        }

	        if (outputStream == null) {
	            outputStream = getResponse().getOutputStream();
	            copier = new ServletOutputStreamCopier(outputStream);
	        }

	        return copier;
	    }

	    @Override
	    public PrintWriter getWriter() throws IOException {
	        if (outputStream != null) {
	            throw new IllegalStateException("getOutputStream() has already been called on this response.");
	        }

	        if (writer == null) {
	            copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
	            writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
	        }

	        return writer;
	    }

	    @Override
	    public void flushBuffer() throws IOException {
	        if (writer != null) {
	            writer.flush();
	        } else if (outputStream != null) {
	            copier.flush();
	        }
	    }

	    public byte[] getCopy() {
	        if (copier != null) {
	            return copier.getCopy();
	        } else {
	            return new byte[0];
	        }
	    }
	}
}