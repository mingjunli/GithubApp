package com.anly.githubapp.data.net.client;

import android.util.Base64;

import com.anly.githubapp.data.net.client.core.ApiEndpoint;
import com.anly.githubapp.data.net.client.core.BaseOkHttpClient;
import com.anly.githubapp.data.net.client.core.BaseRetrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mingjun on 16/7/27.
 */
public class GithubAuthRetrofit extends BaseRetrofit {

    private static final String END_POINT = "https://api.github.com/";

    private String username;
    private String password;

    public GithubAuthRetrofit(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public ApiEndpoint getApiEndpoint() {
        return new ApiEndpoint() {
            @Override
            public String getEndpoint() {
                return END_POINT;
            }
        };
    }

    @Override
    public OkHttpClient getHttpClient() {
        return new AuthHttpClient(username, password).get();
    }

    private class AuthHttpClient extends BaseOkHttpClient {

        private String username;
        private String password;

        public AuthHttpClient(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
            return builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    // https://developer.github.com/v3/auth/#basic-authentication
                    // https://developer.github.com/v3/oauth/#non-web-application-flow
                    String userCredentials = username + ":" + password;
                    String basicAuth =
                            "Basic " + new String(Base64.encode(userCredentials.getBytes(), Base64.DEFAULT));

                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basicAuth.trim());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
    }
}
