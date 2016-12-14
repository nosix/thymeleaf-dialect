package org.anyspirit.thymeleaf.dialect

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring4.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateModeHandler
import org.thymeleaf.templateparser.html.LegacyHtml5TemplateParser

@SpringBootApplication
open class Application {

    /**
     * メッセージリソース
     */
    @Bean
    open fun messageSource() = ReloadableResourceBundleMessageSource().apply {
        setDefaultEncoding("UTF-8")
        setBasename("classpath:messages")
    }

    // Thymeleaf

    private val TEMPLATE_MODE = "LOOSE_HTML5"

    private val POOL_SIZE = Runtime.getRuntime().availableProcessors().let {
        Math.min(if (it <= 2) it else it - 1, 24) // see StandardTemplateModeHandlers
    }

    @Bean
    open fun viewResolver() = ThymeleafViewResolver().apply {
        templateEngine = templateEngine()
    }

    @Bean
    open fun templateEngine() = SpringTemplateEngine().apply {
        templateResolvers = setOf(templateResolver())
        templateModeHandlers = setOf(TemplateModeHandler(
                TEMPLATE_MODE,
                LegacyHtml5TemplateParser(TEMPLATE_MODE, POOL_SIZE),
                SingleQuoteUnEscapeTemplateWriter())) // ' を &#39; にエスケープしない
        setDialect(CustomSpringDialect.Builder()
                .enableAngularJS1()
                .enableUIBootstrap()
                .build())
    }

    @Bean
    open fun templateResolver() = SpringResourceTemplateResolver().apply {
        templateMode = TEMPLATE_MODE
        prefix = "classpath:/templates/"
        suffix = ".html"
        isCacheable = false // TODO: make it true before release
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
