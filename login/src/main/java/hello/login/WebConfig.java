package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") //전체경로
                .excludePathPatterns("/css/**", "/*.ico", "/error"); //화이트리스트
    }

//    @Bean
    public FilterRegistrationBean logFilter() { //FilterRegistrationBean: 필터 빈
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 필터 로그 남기는 LogFilter 객체를 빈으로 등록
        filterRegistrationBean.setOrder(1); // 필터 체인 순서
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 url에 적용할지 /* : 모든 url

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() { //로그인 체크 필터 빈 등록
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2); // 필터 체인 순서 2번에 등록
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
