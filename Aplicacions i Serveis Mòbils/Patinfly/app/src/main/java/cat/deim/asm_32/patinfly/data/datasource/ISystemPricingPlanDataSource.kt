package cat.deim.asm_32.patinfly.data.datasource

import cat.deim.asm_32.patinfly.data.datasource.model.SystemPricingPlanModel

interface ISystemPricingPlanDataSource {
    fun insert(plan: SystemPricingPlanModel): Boolean
    fun getById(planId: String): SystemPricingPlanModel?
    fun update(plan: SystemPricingPlanModel): SystemPricingPlanModel?
    fun delete(planId: String): Boolean
}