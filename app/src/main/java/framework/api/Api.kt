package framework.api

import develop.y.zhzl.detail.model.DetailModel
import develop.y.zhzl.list.model.ListModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * by y on 2016/8/7.
 */
object Api {

    val BASE_API = "https://zhuanlan.zhihu.com/api/"

    interface ZLService {
        @GET("columns/{suffix}/posts")
        fun getList(@Path("suffix") suffix: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Observable<List<ListModel>>

        @GET("posts/{slug}")
        fun getDetail(@Path("slug") slug: Int): Observable<DetailModel>
    }
}
