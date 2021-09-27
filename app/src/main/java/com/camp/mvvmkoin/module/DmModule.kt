package com.camp.mvvmkoin.module

import org.koin.dsl.module
import retrofit2.Retrofit
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.camp.mvvmkoin.Constants.Companion.BASE_URL
import com.camp.mvvmkoin.localdb.ApiInterface
import com.camp.mvvmkoin.localdb.AppDAO
import com.camp.mvvmkoin.localdb.ImageDatabase
import com.camp.mvvmkoin.repo.Repository
import com.camp.mvvmkoin.repo.RepositoryImpl
import com.camp.mvvmkoin.view.AppViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.BuildConfig.DEBUG
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DmModule {
    companion object {
        val apiModule = module {
            fun provideRetrofitApi(retrofit: Retrofit): ApiInterface {
                return retrofit.create(ApiInterface::class.java)
            }
            single { provideRetrofitApi(get()) }
        }

        val viewModelModule = module {
            viewModel {
                AppViewModel(repository = get())
            }
        }

        val repositoryModule = module {
            fun provideRepository(
                api: ApiInterface,
                context: Context,
                dao: AppDAO
            ): Repository {
                return RepositoryImpl(api,context, dao)
            }
            single { provideRepository(get(), androidContext(), get()) }
        }

        val networkModule = module {
            fun provideHttpClient(): OkHttpClient {
                val okHttpClientBuilder = OkHttpClient.Builder()

                if (DEBUG) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
                }
                okHttpClientBuilder.build()
                return okHttpClientBuilder.build()
            }

            fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
                return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    // .client(client) //todo optimmal param
                    .build()
            }

            single { provideHttpClient() }
            single {
                val baseUrl = BASE_URL
                provideRetrofit(get(), baseUrl)
            }
        }

        val databaseModule = module {
            fun provideDatabase(application: Application): ImageDatabase {
                return Room.databaseBuilder(application, ImageDatabase::class.java, "imagesdb")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            fun provideImagesDao(database: ImageDatabase): AppDAO {
                return database.appDao
            }
            single { provideDatabase(androidApplication()) }
            single { provideImagesDao(get()) }
        }
    }
}