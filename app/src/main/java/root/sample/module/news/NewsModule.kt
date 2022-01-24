package root.sample.module.news

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import root.sample.network.api.NewsApiService


@Module
@InstallIn(ViewModelComponent::class)
object NewsModule {

    @Provides
    fun provideNewsRepository(retrofit: Retrofit): NewsRepository {
        return NewsRepository(retrofit.create(NewsApiService::class.java))
    }
}