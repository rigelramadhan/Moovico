package one.reevdev.moovico.core.data.repository

import one.reevdev.moovico.core.data.NetworkBoundResource
import one.reevdev.moovico.core.data.Resource
import one.reevdev.moovico.core.data.local.MovieCategory
import one.reevdev.moovico.core.data.local.datasource.LocalDataSource
import one.reevdev.moovico.core.data.remote.ApiResponse
import one.reevdev.moovico.core.data.remote.datasource.RemoteDataSource
import one.reevdev.moovico.core.data.remote.response.MoviesItem
import com.rivibi.mooviku.core.domain.model.Movie
import one.reevdev.moovico.core.domain.model.MovieDetail
import one.reevdev.moovico.core.domain.model.Review
import one.reevdev.moovico.core.domain.repository.IMovieRepository
import one.reevdev.moovico.core.utils.AppExecutors
import one.reevdev.moovico.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IMovieRepository {
    override fun getNowPlaying(page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getNowPlaying().map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getNowPlaying()
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(
                    DataMapper.mapResponseToEntity(
                        data,
                        MovieCategory.NowPlaying.category
                    )
                )
            }

            override fun shouldFetch(data: List<Movie>?) = true
        }.asFlow()

    override fun getPopular(page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getPopularMovies().map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getPopular()
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(
                    DataMapper.mapResponseToEntity(
                        data,
                        MovieCategory.Popular.category
                    )
                )
            }

            override fun shouldFetch(data: List<Movie>?) = true
        }.asFlow()

    override fun getTopRated(page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getTopRated().map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getTopRated()
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(
                    DataMapper.mapResponseToEntity(
                        data,
                        MovieCategory.TopRated.category
                    )
                )
            }

            override fun shouldFetch(data: List<Movie>?) = true
        }.asFlow()

    override fun getDiscover(page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getDiscover().map { DataMapper.mapEntityToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getDiscover()
            }

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                localDataSource.insertMovies(
                    DataMapper.mapResponseToEntity(
                        data,
                        MovieCategory.Discover.category
                    )
                )
            }

            override fun shouldFetch(data: List<Movie>?) = true

        }.asFlow()

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> =
        remoteDataSource.getMovieDetail(movieId)

    override fun getReviews(movieId: Int): Flow<Resource<List<Review>>> =
        remoteDataSource.getMovieReviews(movieId)

    override fun getMovieRecommendations(movieId: Int): Flow<Resource<List<Movie>>> =
        remoteDataSource.getMovieRecommendations(movieId)

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> =
        remoteDataSource.searchMovies(query)

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavorite(movieId: Int, isFavorite: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavorite(movieId, isFavorite)
        }
    }

    override fun checkFavorite(movieId: Int): Flow<Boolean> = flow {
        emit(localDataSource.checkFavorite(movieId))
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        localDataSource.insertMovies(DataMapper.mapDomainToEntity(movies))
    }
}

