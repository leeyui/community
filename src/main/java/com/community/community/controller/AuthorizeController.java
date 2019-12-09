package com.community.community.controller;

import com.community.community.domain.User;
import com.community.community.dto.AccessTokenDto;
import com.community.community.dto.GithubUser;
import com.community.community.dto.UserDto;
import com.community.community.mapper.UserMapper;
import com.community.community.provider.GithubProvider;
import com.community.community.util.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client_id}")
    private String clientId;

    @Value("${github.client_secret}")
    private String clientSecret;

    @Value("${github.redirect_uri}")
    private String redirectUri;


    @GetMapping(value = "/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if (githubUser != null) {
            String accountId = UserUtil.accountIdTransform("github", String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            User user = null;
            if (userMapper.findByAccountId(accountId) != null) {
                userMapper.updateTokenByAccountId(token,accountId);
                user = userMapper.findByToken(token);
            } else {
                user = new User();
                user.setAvatarUrl(githubUser.getAvatarUrl());
                user.setName(githubUser.getLogin());
                user.setToken(token);
                user.setAccountId(accountId);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userMapper.insert(user);
            }
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            HttpSession session = request.getSession();
            session.setAttribute("user", userDto);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        }
        return "redirect:/";
    }


}
