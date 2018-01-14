package io.github.droidkaigi.confsched2018.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.github.droidkaigi.confsched2018.data.api.DroidKaigiApi
import io.github.droidkaigi.confsched2018.data.api.FeedApi
import io.github.droidkaigi.confsched2018.data.api.FeedFireStoreApi
import io.github.droidkaigi.confsched2018.data.api.SponsorApi
import io.github.droidkaigi.confsched2018.data.api.response.mapper.ApplicationJsonAdapterFactory
import io.github.droidkaigi.confsched2018.data.api.response.mapper.LocalDateTimeAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module internal object NetworkModule {

    @Singleton @Provides @JvmStatic
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    @Singleton @Provides @JvmStatic
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://droidkaigi.jp/2018/sessionize/")
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                        .add(ApplicationJsonAdapterFactory.INSTANCE)
                        .add(LocalDateTime::class.java, LocalDateTimeAdapter())
                        .build()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
    }

    @Singleton @Provides @Named("sponsor") @JvmStatic
    fun provideRetrofitForSponsor(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                // FIXME deploy json to web or app repository
                .baseUrl("https://gist.githubusercontent.com")
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                        .add(ApplicationJsonAdapterFactory.INSTANCE)
                        .add(LocalDateTime::class.java, LocalDateTimeAdapter())
                        .build()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
    }

    @Singleton @Provides @JvmStatic
    fun provideDroidKaigiApi(retrofit: Retrofit): DroidKaigiApi {
        return retrofit.create(DroidKaigiApi::class.java)
    }

    @Singleton @Provides @JvmStatic
    fun provideFeedApi(): FeedApi = FeedFireStoreApi()

    @Singleton @Provides @JvmStatic
    fun provideSponsorApi(@Named("sponsor") retrofit: Retrofit): SponsorApi {
        return retrofit.create(SponsorApi::class.java)
    }
}
