package com.community.community.interceptor;

import com.community.community.domain.User;
import com.community.community.dto.UserDto;
import com.community.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        UserDto userDto = new UserDto();
                        BeanUtils.copyProperties(user, userDto);
                        request.getSession().setAttribute("user", userDto);
                        break;
                    } else {
                        UserDto user1 = (UserDto) request.getSession().getAttribute("user");
                        if (user1 != null) {
                            request.getSession().removeAttribute("user");
                        }
                    }
                }
            }
        }
        request.getSession().setAttribute("pagepath","");
        return true;
    }
}
