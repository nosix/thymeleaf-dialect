package org.anyspirit.thymeleaf.dialect

import org.thymeleaf.Arguments
import org.thymeleaf.dom.Document
import org.thymeleaf.templatewriter.AbstractGeneralTemplateWriter
import java.io.Writer

class SingleQuoteUnEscapeTemplateWriter : AbstractGeneralTemplateWriter() {

    override fun useXhtmlTagMinimizationRules(): Boolean = true

    override fun shouldWriteXmlDeclaration(): Boolean = false

    override fun write(arguments: Arguments, writer: Writer, document: Document) {
        super.write(arguments, UnEscapeWriter(writer), document)
    }

    private class UnEscapeWriter(private val writer: Writer) : Writer() {

        companion object {
            private val SINGLE_QUOTE = "#39;".toList()
        }

        override fun write(buf: CharArray, off: Int, len: Int) {
            val srcBuf = buf.slice(off..(off+len-1))
            var drop = false
            val newBuf = srcBuf.mapIndexedNotNull { i, c ->
                if (drop) {
                    if (c == SINGLE_QUOTE.last()) {
                        drop = false
                    }
                    null
                } else {
                    if (c == '&' && srcBuf.slice((i+1)..(i+SINGLE_QUOTE.size)) == SINGLE_QUOTE) {
                        drop = true
                        '\''
                    } else {
                        c
                    }
                }
            }
            writer.write(newBuf.toCharArray(), 0, newBuf.size)
        }

        override fun flush() {
            writer.flush()
        }

        override fun close() {
            writer.close()
        }
    }
}