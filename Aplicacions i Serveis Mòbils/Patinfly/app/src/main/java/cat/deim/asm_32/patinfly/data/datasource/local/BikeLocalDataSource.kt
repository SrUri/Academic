package cat.deim.asm_32.patinfly.data.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm_32.patinfly.data.datasource.IBikeDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.BikeModel
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import java.io.IOException
import java.io.InputStreamReader

class BikeLocalDataSource private constructor():IBikeDataSource {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: BikeLocalDataSource? = null
        fun getInstance(context: Context)=
            instance ?: synchronized(this) {
                instance ?: BikeLocalDataSource().also {
                    instance = it
                    it.context=context
                    it.loadBikeData()
                }
            }
    }
    private var bicis:MutableList<BikeModel> =mutableListOf()
    private var context:Context?=null
    fun loadBikeData() {
        try {
            context?.assets?.open("bikes.json").use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    val json = reader.readText()
                    bicis = parseJson(json)?.toMutableList()?: mutableListOf()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun parseJson(json: String): List<BikeModel>? {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

        return try {
            data class BikeListResponse(
                @SerializedName("bike") val bikes: List<BikeModel>?
            )
            val response = gson.fromJson(json, BikeListResponse::class.java)
            response.bikes ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    override fun insert(bike:BikeModel):Boolean{
        bicis.add(bike)
        return true
    }

    override fun insertOrUpdate(bike: BikeModel): Boolean {
        val i=bicis.indexOfFirst{it.uuid==bike.uuid}
        return if (i==-1) {
            insert(bike)
        }else{
            update(bike)!=null
        }
    }
    override fun getAll():List<BikeModel> =bicis.toList()
    override fun getById(uuid: String): BikeModel? {
        return bicis.firstOrNull{it.uuid==uuid}
    }
    override fun update(bike: BikeModel): BikeModel? {
        val i = bicis.indexOfFirst { it.uuid == bike.uuid }
        return if (i != -1) {
            val antigua = bicis[i]
            bicis[i] = bike
            antigua
        } else {
            null
        }
    }
    override fun delete(uuid: String): Boolean {
        val elim = bicis.any {it.uuid == uuid }
        bicis.removeIf { it.uuid == uuid}
        return elim
    }
}