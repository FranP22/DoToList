package hr.tvz.android.projectfranprizmic.Activity.Model

import android.util.Log
import com.google.gson.annotations.SerializedName
import hr.tvz.android.listaprizmic.classes.database.ServiceInterface
import hr.tvz.android.projectfranprizmic.Activity.Model.Callback.IValidCallBack
import hr.tvz.android.projectfranprizmic.Database.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.lang.Exception
import kotlin.concurrent.thread

class User(
    @SerializedName("Id")
    private val id: Int,
    @SerializedName("Username")
    private val username: String?,
    @SerializedName("Password")
    private val password: String?
) : IUser {
    private var registerpassword: String = ""
    private var isRegistering: Boolean = false
    private val API_URL = "http://10.0.2.2:8080/api/"

    override fun getId(): Int {
        return id
    }

    override fun getUsername(): String? {
        return username
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getRegisterPassword(): String? {
        return registerpassword
    }

    override fun startRegister(secondpassword: String) {
        isRegistering = true
        registerpassword = secondpassword
    }

    override fun isValid(callback: IValidCallBack) {
        var valid: UserValid = UserValid.ERROR
        var notvalid = User(-1,"","")
        if (username!!.isEmpty()) {
            valid = UserValid.EMPTY_USERNAME
            callback.onResult(valid, notvalid)
        } else if (password!!.isEmpty()) {
            valid = UserValid.EMPTY_PASSWORD
            callback.onResult(valid, notvalid)
        } else {
            val client: ServiceInterface = ServiceGenerator().createService(
                ServiceInterface::class.java,
                API_URL
            )

            if (isRegistering) {
                if (password != registerpassword) {

                    valid = UserValid.PASSWORD_MISMATCH
                    callback.onResult(valid, notvalid)

                } else {

                    val usercall: Call<User> = client.getUsername(username)
                    usercall.enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if(response.isSuccessful) {

                                valid = UserValid.USERNAME_EXISTS
                                callback.onResult(valid, notvalid)

                            }else if(response.code() == 404){

                                val usercreatecall: Call<User> = client.insertUser(this@User)
                                usercreatecall.enqueue(object : Callback<User> {
                                    override fun onResponse(call: Call<User>, response: Response<User>) {
                                        if(response.isSuccessful){

                                            valid = UserValid.USER_CREATED
                                            callback.onResult(valid, response.body()!!)

                                        }else{

                                            callback.onResult(valid, notvalid)

                                        }
                                    }

                                    override fun onFailure(call: Call<User>, t: Throwable) {

                                        callback.onResult(valid, notvalid)

                                    }
                                })

                            }else{

                                callback.onResult(valid, notvalid)
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {

                            callback.onResult(valid, notvalid)

                        }
                    })
                }

            } else {
                val usercall: Call<User> = client.getUserLogin(username, password)
                usercall.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {

                            valid = UserValid.VALID
                            callback.onResult(valid, response.body()!!)

                        } else {

                            valid = UserValid.NOTFOUND
                            callback.onResult(valid, notvalid)

                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {

                        callback.onResult(valid, notvalid)

                    }
                })
            }
        }
    }
}