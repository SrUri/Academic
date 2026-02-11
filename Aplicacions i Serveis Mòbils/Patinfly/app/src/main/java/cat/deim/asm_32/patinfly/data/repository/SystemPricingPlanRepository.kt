package cat.deim.asm_32.patinfly.data.repository

import cat.deim.asm_32.patinfly.data.datasource.ISystemPricingPlanDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.SystemPricingPlanModel
import cat.deim.asm_32.patinfly.domain.models.SystemPricingPlan
import cat.deim.asm_32.patinfly.domain.repository.ISystemPricingPlanRepository

class SystemPricingPlanRepository(private val SystemPricingPlanDataSoure: ISystemPricingPlanDataSource) : ISystemPricingPlanRepository {

    override fun insert(plan: SystemPricingPlan): Boolean {
        return SystemPricingPlanDataSoure.insert(SystemPricingPlanModel.fromDomain(plan))
    }


    override fun getById(planId: String): SystemPricingPlan? {
        return SystemPricingPlanDataSoure.getById(planId)?.toDomain()
    }

    override fun update(plan: SystemPricingPlan): Boolean {
        return SystemPricingPlanDataSoure.update(SystemPricingPlanModel.fromDomain(plan)) != null
    }

    override fun delete(planId: String): Boolean {
        return SystemPricingPlanDataSoure.delete(planId)
    }
}