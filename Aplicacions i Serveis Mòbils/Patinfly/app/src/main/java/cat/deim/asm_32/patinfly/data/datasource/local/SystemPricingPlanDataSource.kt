package cat.deim.asm_32.patinfly.data.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import cat.deim.asm_32.patinfly.data.datasource.ISystemPricingPlanDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.SystemPricingPlanModel
import java.io.IOException
import java.io.InputStreamReader
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException

class SystemPricingPlanDataSource private constructor(): ISystemPricingPlanDataSource {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SystemPricingPlanDataSource? = null
        fun getInstance(context: Context)=
            instance ?: synchronized(this) {
                instance ?: SystemPricingPlanDataSource().also {
                    instance = it
                    it.context=context
                    it.loadPricingData()
                }
            }
    }

    private var pricingPlan:SystemPricingPlanModel?=null
    private var context:Context?=null
    fun loadPricingData() {
        try {
            context?.assets?.open("plans.json").use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    val json = reader.readText()
                    pricingPlan = parseJson(json)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun parseJson(json: String): SystemPricingPlanModel? {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()
        return try {
            gson.fromJson(json, SystemPricingPlanModel::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }
    override fun insert(plan: SystemPricingPlanModel): Boolean {
        pricingPlan=plan
        return true
    }
    override fun getById(planId: String): SystemPricingPlanModel? {
        return pricingPlan?.takeIf { it.dataPlan.planId == planId }
    }

    override fun update(plan: SystemPricingPlanModel): SystemPricingPlanModel? {
        val antic = pricingPlan
        pricingPlan=plan
        return antic
    }

    override fun delete(planId: String): Boolean {
        return if (pricingPlan?.dataPlan?.planId == planId) {
            pricingPlan = null
            true
        } else {
            false
        }
    }
}