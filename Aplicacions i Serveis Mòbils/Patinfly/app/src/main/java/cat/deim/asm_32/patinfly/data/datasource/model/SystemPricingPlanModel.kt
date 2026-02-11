package cat.deim.asm_32.patinfly.data.datasource.model

import cat.deim.asm_32.patinfly.domain.models.*

data class SystemPricingPlanModel(
    val lastUpdated: String,
    val ttl: String,
    val version: Double,
    val dataPlan: InformationModel
){
    fun toDomain(): SystemPricingPlan = SystemPricingPlan(
        lastUpdated = this.lastUpdated,
        ttl = this.ttl,
        version = this.version,
        dataPlan = this.dataPlan.toDomain()
    )
    companion object {
        fun fromDomain(plan: SystemPricingPlan): SystemPricingPlanModel = SystemPricingPlanModel(
            lastUpdated = plan.lastUpdated,
            ttl = plan.ttl,
            version = plan.version,
            dataPlan = InformationModel.fromDomain(plan.dataPlan)
        )
    }
}
data class InformationModel(
    val planId: String,
    val name: TextTypeModel,
    val currency: String,
    val price: Double,
    val isTaxable: Boolean,
    val description: TextTypeModel,
    val perKmPricing: PerKmPricingModel,
    val perMinPricing: PerMinPricingModel
){
    fun toDomain(): Information = Information(
        planId = this.planId,
        name = this.name.toDomain(),
        currency = this.currency,
        price = this.price,
        isTaxable = this.isTaxable,
        description = this.description.toDomain(),
        perKmPricing = this.perKmPricing.toDomain(),
        perMinPricing = this.perMinPricing.toDomain()
    )
    companion object {
        fun fromDomain(info: Information): InformationModel = InformationModel(
            planId = info.planId,
            name = TextTypeModel.fromDomain(info.name),
            currency = info.currency,
            price = info.price,
            isTaxable = info.isTaxable,
            description = TextTypeModel.fromDomain(info.description),
            perKmPricing = PerKmPricingModel.fromDomain(info.perKmPricing),
            perMinPricing = PerMinPricingModel.fromDomain(info.perMinPricing)
        )
    }
}
data class TextTypeModel(
    val text: String,
    val language: String
){
    fun toDomain(): TextType = TextType(
        text = this.text,
        language = this.language
    )
    companion object {
        fun fromDomain(textType: TextType): TextTypeModel = TextTypeModel(
            text = textType.text,
            language = textType.language
        )
    }
}
data class PerKmPricingModel(
    val start: Double,
    val rate: Double,
    val interval: Double
){
    fun toDomain(): PerKmPricing = PerKmPricing(
        start = this.start,
        rate = this.rate,
        interval = this.interval
    )
    companion object {
        fun fromDomain(pricing: PerKmPricing): PerKmPricingModel = PerKmPricingModel(
            start = pricing.start,
            rate = pricing.rate,
            interval = pricing.interval
        )
    }
}
data class PerMinPricingModel(
    val start: Double,
    val rate: Double,
    val interval: Double
){
    fun toDomain(): PerMinPricing = PerMinPricing(
        start = this.start,
        rate = this.rate,
        interval = this.interval
    )
    companion object {
        fun fromDomain(pricing: PerMinPricing): PerMinPricingModel = PerMinPricingModel(
            start = pricing.start,
            rate = pricing.rate,
            interval = pricing.interval
        )
    }
}