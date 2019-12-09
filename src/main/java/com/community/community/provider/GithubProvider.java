package com.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.community.community.dto.AccessTokenDto;
import com.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    private static final MediaType HEADER
            = MediaType.get("application/json; charset=utf-8");

    public String getAccessToken(AccessTokenDto accessTokenDto) {
        String url = "https://github.com/login/oauth/access_token";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(HEADER, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String res = response.body().string();
            String s = res.split("&")[0];
            String accessToken = s.split("=")[1];
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public GithubUser getGithubUser(String accessToken) {
        String url = "https://api.github.com/user" + "?access_token=" + accessToken;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            GithubUser githubUser = JSON.parseObject(response.body().string(), GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


