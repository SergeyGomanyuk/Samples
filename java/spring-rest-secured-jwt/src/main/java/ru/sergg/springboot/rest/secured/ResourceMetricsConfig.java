package ru.sergg.springboot.rest.secured;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnClass({ Servlet.class, ServletRegistration.class, OncePerRequestFilter.class, HandlerMapping.class })
//@AutoConfigureAfter(MetricRepositoryAutoConfiguration.class)
//@ConditionalOnProperty(name = "com.amdocs.digital.ms.resources.metrics.filter.enabled", matchIfMissing = true)
public class ResourceMetricsConfig {
	@Bean
	public CustomRequestFilter customRequestFilter() {
		return new CustomRequestFilter();
	}

//	@Bean
//	public PerformActivityMetricsAspect performActivityMetricsAspect() {
//		return Aspects.aspectOf(PerformActivityMetricsAspect.class);
//	}
}