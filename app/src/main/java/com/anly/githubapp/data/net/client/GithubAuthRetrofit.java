package com.anly.githubapp.data.net.client;

import android.text.TextUtils;
import android.util.Base64;

import com.anly.githubapp.common.util.AppLog;
import com.anly.githubapp.data.net.client.core.ApiEndpoint;
import com.anly.githubapp.data.net.client.core.BaseOkHttpClient;
import com.anly.githubapp.data.net.client.core.BaseRetrofit;

import org.w3c.dom.Text;

import java.io.IOException;

import javax.inject.Inject;

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

    @Inject
    public GithubAuthRetrofit() {
    }

    public void setAuthInfo(String username, String password) {
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

            AppLog.d("username:" + username);
            AppLog.d("password:" + password);

            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {


                        // https://developer.github.com/v3/auth/#basic-authentication
                        // https://developer.github.com/v3/oauth/#non-web-application-flow
                        String userCredentials = username + ":" + password;

                        AppLog.d("userCredentials:" + userCredentials);

                        String basicAuth =
                                "Basic " + new String(Base64.encode(userCredentials.getBytes(), Base64.DEFAULT));

                        AppLog.d("basicAuth:" + basicAuth);

                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", basicAuth.trim());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
            }

            return builder;
        }
    }
}
