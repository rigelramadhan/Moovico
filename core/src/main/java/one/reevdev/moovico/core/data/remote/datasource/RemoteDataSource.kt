package one.reevdev.moovico.core.data.remote.datasource

import android.util.Log
import one.reevdev.moovico.core.data.Resource
import one.reevdev.moovico.core.data.remote.ApiResponse
import one.reevdev.moovico.core.data.remote.ApiResponseMethod
import one.reevdev.moovico.core.data.remote.network.ApiService
import one.reevdev.moovico.core.data.remote.response.MoviesItem
import com.rivibi.mooviku.core.domain.model.Movie
import one.reevdev.moovico.core.domain.model.MovieDetail
import one.reevdev.moovico.core.domain.model.Review
import one.reevdev.moovico.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun getNowPlaying(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.getNowPlaying(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getPopular(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return channelFlow {
            try {
                val response = apiService.getPopularMovies(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    send(ApiResponse.Success(data))
                } else {
                    send(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                Log.e("MOOVIKU_ERROR", e.message.toString())
                send(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getTopRated(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return channelFlow {
            try {
                val response = apiService.getTopRatedMovies(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    send(ApiResponse.Success(data))
                } else {
                    send(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                Log.e("MOOVIKU_ERROR", e.message.toString())
                send(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getDiscover(
        page: Int = 1
    ): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val response = apiService.getDiscover(page = page)
                val data = response.results

                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Error(ApiResponseMethod.ERROR_404))
                }
            } catch (e: Exception) {
                Log.e("MOOVIKU_ERROR", e.message.toString())
                emit(ApiResponse.Error(ApiResponseMethod.ERROR_ELSE, e.message.toString()))
            }
        }
    }

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return channelFlow {
            try {
                val response = apiService.getMovieDetail(movieId = movieId)
                val data = DataMapper.mapDetailResponseToDomain(response)
                send(Resource.Success(data))
            } catch (e: Exception) {
                Log.e("MOOVIKU_ERROR", e.message.toString())
                send(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getMovieReviews(movieId: Int): Flow<Resource<List<Review>>> {
        return flow {
            try {
                val response = apiService.getMovieReviews(movieId = movieId)
                val data = DataMapper.mapReviewsResponseToDomain(response.results)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getMovieRecommendations(movieId: Int): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                val response = apiService.getMovieRecommendations(movieId = movieId)
                val entities = DataMapper.mapResponseToEntity(response.results, category = "Others")
                val data = DataMapper.mapEntityToDomain(entities)

                if (data.isNotEmpty()) {
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error(ApiResponseMethod.ERROR_404.name))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }

    fun searchMovies(query: String, page: Int = 1): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                val response = apiService.getSearch(query = query, page = page)
                val entities = DataMapper.mapResponseToEntity(response.results, category = "Others")
                val data = DataMapper.mapEntityToDomain(entities)

                if (data.isNotEmpty()) {
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error(ApiResponseMethod.ERROR_404.name))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }
    }
}