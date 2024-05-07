package study.board;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.board.filter.LogFilter;
import study.board.filter.LoginCheckFilter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 만든 필터를 넣는다.
        filterRegistrationBean.setOrder(1); // 순서
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 url 패턴을 할 것인지

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 만든 필터를 넣는다.
        filterRegistrationBean.setOrder(2); // 순서
        filterRegistrationBean.addUrlPatterns("/*"); // "/*"으로 모든 요청에 해당 필터 적용

        return filterRegistrationBean;
    }
}
