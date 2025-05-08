package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j // 로그 찍겠다
@Aspect // 이 클래스는 AOP에서 관점 담당하겠다
@Component // 빈에 등록하겠다
@RequiredArgsConstructor // 생성자
public class AdminAccessLoggingAspect {

    private final HttpServletRequest request;

    // 현재 문제점 1. @After => 메서드가 >끝난 후< 실행할 로직 !! 메서드 실행 전 동작시키려면 @Before로 해야함
    // 2. execution 뒤 경로가 UserController.getUser로 설정되어 있음. UserAdminController.changeUserRole로 설정해줘야 함
    @Before("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public void logAfterChangeUserRole(JoinPoint joinPoint) {
        String userId = String.valueOf(request.getAttribute("userId"));
        String requestUrl = request.getRequestURI();
        LocalDateTime requestTime = LocalDateTime.now();

        log.info("Admin Access Log - User ID: {}, Request Time: {}, Request URL: {}, Method: {}",
                userId, requestTime, requestUrl, joinPoint.getSignature().getName());
    }
}
