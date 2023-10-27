package one.reevdev.moovico.core.di

import android.content.Context
import androidx.room.Room
import one.reevdev.moovico.core.data.local.room.database.MovieDao
import one.reevdev.moovico.core.data.local.room.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideDao(database: MovieDatabase): MovieDao = database.getDao()
}