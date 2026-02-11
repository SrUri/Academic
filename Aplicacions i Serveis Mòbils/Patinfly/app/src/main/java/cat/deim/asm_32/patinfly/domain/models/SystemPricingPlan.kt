package cat.deim.asm_32.patinfly.domain.models

data class SystemPricingPlan(
    val lastUpdated: String,
    val ttl: String,
    val version: Double,
    val dataPlan: Information
)

data class Information(
    val planId: String,
    val name: TextType,
    val currency: String,
    val price: Double,
    val isTaxable: Boolean,
    val description: TextType,
    val perKmPricing: PerKmPricing,
    val perMinPricing: PerMinPricing
)
data class TextType(
    val text: String,
    val language: String
)
data class PerKmPricing(
    val start: Double,
    val rate: Double,
    val interval: Double
)
data class PerMinPricing(
    val start: Double,
    val rate: Double,
    val interval: Double
)
