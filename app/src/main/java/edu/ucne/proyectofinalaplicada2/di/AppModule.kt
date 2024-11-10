package edu.ucne.proyectofinalaplicada2.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.proyectofinalaplicada2.data.local.database.FoodiePlaceDb
import edu.ucne.proyectofinalaplicada2.data.remote.API.CategoriaAPI
import edu.ucne.proyectofinalaplicada2.data.remote.API.FoodiePlaceApi
import edu.ucne.proyectofinalaplicada2.data.remote.API.ReservacionesAPI
import edu.ucne.proyectofinalaplicada2.data.remote.API.ReviewAPI
import edu.ucne.proyectofinalaplicada2.data.remote.API.UsuarioApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    const val BASE_URL = "https://foodieplaceapi.azurewebsites.net/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesFoodiePlaceApi(moshi: Moshi): FoodiePlaceApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(FoodiePlaceApi::class.java)
    }

    @Provides
    @Singleton
    fun providesFoodiePlaceDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            FoodiePlaceDb::class.java,
            "FoodiePlace.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesReseñasAPI(moshi: Moshi): ReviewAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ReviewAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesCategoriaAPI(moshi: Moshi): CategoriaAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CategoriaAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesUsuarioApi(moshi: Moshi): UsuarioApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UsuarioApi::class.java)
    }



    @Provides
    @Singleton
    fun providesReservacionesAPI(moshi: Moshi): ReservacionesAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ReservacionesAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesReservacionesDao(db: FoodiePlaceDb) = db.ReservacionesEntity






    @Provides
    @Singleton
    fun providesUsuarioDao(foodiePlaceDb: FoodiePlaceDb) = foodiePlaceDb.usuarioDao()

    @Provides
    @Singleton
    fun providesReseñasDao(foodiePlaceDb: FoodiePlaceDb) = foodiePlaceDb.reviewDao()

    @Provides
    @Singleton
    fun providesCategoriaDao(foodiePlaceDb: FoodiePlaceDb) = foodiePlaceDb.categoriaDao()
}
