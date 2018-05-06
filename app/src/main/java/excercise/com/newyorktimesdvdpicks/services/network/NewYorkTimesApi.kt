package excercise.com.newyorktimesdvdpicks.services.network

import excercise.com.newyorktimesdvdpicks.models.DvdPick
import excercise.com.newyorktimesdvdpicks.models.DvdPickListResponse
import excercise.com.newyorktimesdvdpicks.utils.Constants.API_KEY
import excercise.com.newyorktimesdvdpicks.utils.Constants.BASE_URL
import excercise.com.newyorktimesdvdpicks.utils.Constants.BY_DATE
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
    Retrofit Required Interface To Make Network Requests to New York Times API
 */
internal interface NewYorkTimesApiInterface {

    @GET("reviews/dvd-picks.json")
    fun dvdPickListByDate(@Query("order") order: String, @Query("api-key") apiKey: String): Flowable<DvdPickListResponse>

}

/*
    New York Times Api Handler.
    Use this class for writing or using any network requests to the New York Times Api.
 */
class NewYorkTimesApi {

    //Network Request Logging
    private val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)


    private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    //Retrofit With RxJava2 and Moshi support
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

    private val newYorkTimesApiInterface = retrofit.create(NewYorkTimesApiInterface::class.java)

    //Return Flowable of List<DvdPick> From Api or Error
    fun getDvdPickListByDate(): Flowable<List<DvdPick>> =
            newYorkTimesApiInterface.dvdPickListByDate(BY_DATE, API_KEY)
                    .map {
                        it.results
                    }

}

