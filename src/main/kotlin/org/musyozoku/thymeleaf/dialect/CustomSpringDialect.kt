package org.musyozoku.thymeleaf.dialect

import org.thymeleaf.processor.IProcessor
import org.thymeleaf.spring4.dialect.SpringStandardDialect
import org.thymeleaf.standard.processor.attr.StandardSingleRemovableAttributeModifierAttrProcessor

class CustomSpringDialect(
        val attributesToBeModified: List<String>,
        val elementsToBeRenamed: List<String>
) : SpringStandardDialect() {

    override fun getProcessors(): MutableSet<IProcessor> = super.getProcessors().apply {
        addAll(attributesToBeModified.map(::StandardSingleRemovableAttributeModifierAttrProcessor))
        addAll(elementsToBeRenamed.map(::RenameElementProcessor))
    }

    /**
     * Builder build CustomSpringDialect object.
     *
     * CustomSpringDialect.Builder()
     *   .toBeModified("ng-init", "ng-class")
     *   .toBeRenamed("script")
     *   .build()
     */
    class Builder {

        private val attributesToBeModified = mutableListOf<String>()
        private val elementsToBeRenamed = mutableListOf<String>()

        fun build() = CustomSpringDialect(attributesToBeModified, elementsToBeRenamed)

        fun toBeModified(vararg attributes: String) = apply {
            attributesToBeModified.addAll(attributes)
        }

        fun toBeRenamed(vararg elements: String) = apply {
            elementsToBeRenamed.addAll(elements)
        }
    }
}