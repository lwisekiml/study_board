//package study.board;
//
//import jakarta.servlet.Filter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import study.board.Interceptor.LogInterceptor;
//import study.board.Interceptor.LoginCheckInterceptor;
//import study.board.argumentresolver.LoginMemberArgumentResolver;
//import study.board.filter.LogFilter;
//import study.board.filter.LoginCheckFilter;
//
//import java.util.List;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new LoginMemberArgumentResolver());
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**", "/*.ico", "/error");
//
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/member/add", "/member/welcome", "/login", "/logout", "/css/**", "/*.ico", "/error");
//    }
//
////    @Bean
//    public FilterRegistrationBean logFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new LogFilter()); // 만든 필터를 넣는다.
//        filterRegistrationBean.setOrder(1); // 순서
//        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 url 패턴을 할 것인지
//
//        return filterRegistrationBean;
//    }
//
////    @Bean
//    public FilterRegistrationBean loginCheckFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 만든 필터를 넣는다.
//        filterRegistrationBean.setOrder(2); // 순서
//        filterRegistrationBean.addUrlPatterns("/*"); // "/*"으로 모든 요청에 해당 필터 적용
//
//        return filterRegistrationBean;
//    }
//}
