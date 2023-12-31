package one.reevdev.moovico.core.data.local.room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import one.reevdev.moovico.core.data.local.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMoviesByCategory(category: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMoviesWithFavorite(movies: List<MovieEntity>) {
        val filteredMovies = movies.map { movie ->
            if (isMovieFavorite(movie.id)) {
                movie.favorite = true
            }

            movie
        }

        insertMovies(filteredMovies)
    }

    @Query("SELECT * FROM movies WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET favorite = :isFavorite WHERE id = :movieId")
    fun setFavorite(movieId: Int, isFavorite: Boolean)

    @Query("SELECT EXISTS (SELECT * FROM movies WHERE id = :movieId AND favorite = 1)")
    suspend fun isMovieFavorite(movieId: Int): Boolean
}