package excercise.com.newyorktimesdvdpicks.models

import com.squareup.moshi.Json

/*
    Original Response From New York Times Api
    Results contains all of the Dvd Picks
 */
class DvdPickListResponse (
    val status: String,
    val copyright: String,
    @Json(name="has_more")
    val hasMore: Boolean,
    val results: List<DvdPick>
)
