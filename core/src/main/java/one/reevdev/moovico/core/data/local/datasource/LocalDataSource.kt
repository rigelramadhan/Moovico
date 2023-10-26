package one.reevdev.moovico.core.data.local.datasource

import one.reevdev.moovico.core.data.local.MovieCategory
import one.reevdev.moovico.core.data.local.room.database.MovieDao
import one.reevdev.moovico.core.data.local.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    fun getPopularMovies(): Flow<List<MovieEntity>> {
        return movieDao.getMoviesByCategory(MovieCategory.Popular.category)
    }

    fun getNowPlaying(): Flow<List<MovieEntity>> {
        return movieDao.getMoviesByCategory(MovieCategory.NowPlaying.category)
    }

    fun getTopRated(): Flow<List<MovieEntity>> {
        return movieDao.getMoviesByCategory(MovieCategory.TopRated.category)
    }

    fun getDiscover(): Flow<List<MovieEntity>> {
        return movieDao.getMoviesByCategory(MovieCategory.Discover.category)
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMoviesWithFavorite(movies)

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun setFavorite(movieId: Int, isFavorite: Boolean) =
        movieDao.setFavorite(movieId, isFavorite)

    suspend fun checkFavorite(movieId: Int) = movieDao.isMovieFavorite(movieId)
}