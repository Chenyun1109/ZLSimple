package framework.network;

import java.util.List;

import develop.y.zhzl.detail.model.DetailModel;
import develop.y.zhzl.list.model.ListModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * by y on 2016/8/7.
 */
public class Api {

    public static final String BASE_API = "https://zhuanlan.zhihu.com/api/";

    private static final String LIST_API = "columns/";

    private static final String DETAIL_API = "posts/";

    public interface ZLService {
        @GET(Api.LIST_API + "{suffix}/posts")
        Observable<List<ListModel>> getList(@Path("suffix") String suffix, @Query("limit") int limit, @Query("offset") int offset);

        @GET(Api.DETAIL_API + "{slug}")
        Observable<DetailModel> getDetail(@Path("slug") int slug);
    }
}
