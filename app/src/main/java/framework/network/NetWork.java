package framework.network;

import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * by y on 2016/8/7.
 */
public class NetWork {

    private static Api.ZLService zlApi;

    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static OkHttpClient getOkHttp() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new LogInterceptor())
                .build();
    }

    public static Api.ZLService getZlApi() {
        if (zlApi == null) {
            zlApi = getRetrofit().create(Api.ZLService.class);
        }
        return zlApi;
    }


    private static Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .client(getOkHttp())
                .baseUrl(Api.BASE_API)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }


    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            KLog.i(chain.request().toString());
//            KLog.json(content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    }
}
