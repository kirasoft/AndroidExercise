package excercise.com.newyorktimesdvdpicks.interfaces

/*
    Error Callback That Allows The ViewModels To Notify The View Of Any Errors
 */
interface ErrorCallback {

    fun onError(error: Throwable)

}