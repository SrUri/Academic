package cat.deim.asm_32.patinfly.domain.repository

import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan

interface ISystemPricingPlanRepository {
    fun insert(systemPricingPlan: SystemPricingPlan): Boolean
    fun getById(planId: String): SystemPricingPlan?
    fun update(systemPricingPlan: SystemPricingPlan): Boolean
    fun delete(planId: String): Boolean
}