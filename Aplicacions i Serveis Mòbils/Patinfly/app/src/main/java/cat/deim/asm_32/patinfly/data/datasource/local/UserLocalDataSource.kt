package cat.deim.asm_32.patinfly.data.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm_32.patinfly.data.datasource.IUserDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.UserModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.io.IOException
import java.io.InputStreamReader

class UserLocalDataSource private constructor():IUserDataSource {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: UserLocalDataSource? = null
        fun getInstance(context: Context)=
            instance ?: synchronized(this) {
                instance ?: UserLocalDataSource().also {
                    instance = it
                    it.context=context
                    it.loadUserData()
                }
            }
    }
    private var mUserModel:UserModel?=null
    private var context:Context?=null
    fun loadUserData() {
        try {
            context?.assets?.open("user.json").use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    val json = reader.readText()
                    mUserModel = parseJson(json)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun parseJson(json:String):UserModel?{
        val gson= GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()
        return try {
            gson.fromJson(json, UserModel::class.java)
        }catch (e:JsonSyntaxException){
            e.printStackTrace()
            null
        }
    }
    override fun insert(user: UserModel): Boolean{
        mUserModel=user
        return true
    }
    override fun insertOrUpdate(userModel: UserModel): Boolean{
        return if(mUserModel==null){
            insert(userModel)
    }else{
        update(userModel)!=null
        }
    }
    override fun getUser(): UserModel?= mUserModel
    override fun getById(uuid:String): UserModel?{
        return mUserModel?.takeIf {it.uuid==uuid}
    }
    override fun update(userModel: UserModel): UserModel?{
        val antiguo= mUserModel
        mUserModel=userModel
        return antiguo
    }
    override fun deleteUser(uuid:String): UserModel? {
        val eliminat= mUserModel?.takeIf{it.uuid==uuid}
        mUserModel=null
        return eliminat
    }
}