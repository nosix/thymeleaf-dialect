package org.musyozoku.thymeleaf.dialect

import org.thymeleaf.Arguments
import org.thymeleaf.dom.Element
import org.thymeleaf.processor.ProcessorResult
import org.thymeleaf.processor.element.AbstractElementProcessor

/**
 * RenameElementProcessor rename th:elementName to elementName.
 */
class RenameElementProcessor(val elementName: String) : AbstractElementProcessor(elementName) {

    override fun getPrecedence(): Int = 100000 // same as StandardBlockElementProcessor

    override fun processElement(args: Arguments, e: Element): ProcessorResult {
        e.parent.run {
            insertBefore(e, e.cloneElementNodeWithNewName(this, elementName, false))
            removeChild(e)
        }
        return ProcessorResult.OK
    }
}