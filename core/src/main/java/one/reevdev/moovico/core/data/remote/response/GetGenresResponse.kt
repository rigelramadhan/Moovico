package one.reevdev.moovico.core.data.remote.response

import com.google.gson.annotations.SerializedName
import one.reevdev.moovico.core.data.remote.response.GenresItem

data class GetGenresResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>
)
