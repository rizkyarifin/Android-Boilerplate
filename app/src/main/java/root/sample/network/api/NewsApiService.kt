package root.sample.network.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import root.sample.model.entity.NewsEntity

interface NewsApiService {

    @GET("everything?q=sports&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348")
    suspend fun requestListNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsEntity
}
