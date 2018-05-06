package excercise.com.newyorktimesdvdpicks.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

/*
    POJO Class for Dvd Picks
 */
@Entity(tableName="dvd_picks")
data class DvdPick(
        @PrimaryKey
        @Json(name="display_title")
        val movieTitle: String,
        @Json(name="mpaa_rating")
        val movieRating: String,
        @Json(name="byline")
        val reviewer: String,
        val headline: String,
        @Json(name="summary_short")
        val shortSummary: String,
        @Json(name="publication_date")
        val publicationDate: String,
        @Embedded
        val multimedia: Multimedia
) {
    //Use this function to access the Headline without Review: prefixing the string
    fun headlineText(): String = headline.removePrefix("Review:")
}
