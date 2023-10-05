package com.mel.allmovies.di

import com.mel.allmovies.utilities.Constants
import com.mel.allmovies.data.datasource.remote.IRemoteSourceMovies
import com.mel.allmovies.data.datasource.remote.RTService
import com.mel.allmovies.data.datasource.remote.RemoteSourceMovies
import com.mel.allmovies.repository.listmovies.IMoviesRepository
import com.mel.allmovies.repository.listmovies.MoviesRepository
import com.mel.allmovies.viewModel.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module{
    factory { provideRetrofit() }
    single<RTService>{ provideRTServiceClient(get()) }
    singleOf(::RemoteSourceMovies) { bind<IRemoteSourceMovies>() }
    singleOf(::MoviesRepository) { bind<IMoviesRepository>() }
    viewModel { MainViewModel(get()) }
}

private fun provideOkHttpClient(): OkHttpClient{
    val interceptor = MyInterceptor()
    val loggingInterceptor: HttpLoggingInterceptor = provideLoggingInterceptor()
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(loggingInterceptor).build()
}
private fun provideRetrofit(): Retrofit{
    val okHttpClient = provideOkHttpClient()
    return Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}
private fun provideRTServiceClient(retrofit: Retrofit): RTService{
    return retrofit.create(RTService::class.java)
}
private fun provideLoggingInterceptor(): HttpLoggingInterceptor{
    return HttpLoggingInterceptor().apply {
        this.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        this.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}

class MyInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Constants.api_key}")
            .build()
        return chain.proceed(request)
    }
}