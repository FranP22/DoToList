package hr.tvz.android.listaprizmic.classes.database

import hr.tvz.android.projectfranprizmic.Activity.Model.ToDo
import hr.tvz.android.projectfranprizmic.Activity.Model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceInterface {
    @GET("User")
    fun getAllUsers(): Call<MutableList<User>>

    @GET("User")
    fun getUserLogin(@Query("username") username: String, @Query("password") password: String): Call<User>

    @GET("User")
    fun getUsername(@Query("username") username: String): Call<User>

    @POST("User")
    fun insertUser(@Body item: User?): Call<User>

    @DELETE("User/{id}")
    fun deleteUser(@Path("id") id: Int): Call<ResponseBody>

    @GET("ToDo")
    fun getAllToDos(): Call<MutableList<ToDo>>

    @GET("ToDo/{id}")
    fun getAllToDoFromUser(@Path("id") id: Int): Call<MutableList<ToDo>>

    @POST("ToDo")
    fun insertToDo(@Body item: ToDo?): Call<ToDo>

    @PUT("ToDo/{id}")
    fun putToDo(@Path("id") id: Int, @Body item: ToDo?): Call<ToDo>

    @DELETE("ToDo/{id}")
    fun deleteToDo(@Path("id") id: Int): Call<ToDo>

    @DELETE("ToDo/clear/{userid}")
    fun clearToDo(@Path("userid") userid: Int): Call<ToDo>
}