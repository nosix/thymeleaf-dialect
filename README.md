# thymeleaf-dialect

Thymeleaf Dialect for custom tags in Kotlin.

For example, when you want to use Thymeleaf with AngularJS 1 and UI Bootstrap, this library may be useful.

## Testing Version

Thymeleaf : 2.1.5

## Usage

build.gradle :

```
repositories {
	mavenCentral()
	maven {
		url 'http://nosix.github.io/thymeleaf-dialect/'
	}
}

dependencies {
	compile 'org.musyozoku:thymeleaf-dialect:0.0.2'
}
```

WebApplication.kt :

```
@SpringBootApplication
open class WebApplication {

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
                SingleQuoteUnEscapeTemplateWriter())) // ' is not escaped to &#39;
        setDialect(CustomSpringDialect.Builder()
                .enableAngularJS1()
                .enableUIBootstrap()
                .toBeModified("my-attr")
                .toBeRenamed("my-tag")
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
```

If you want to escape `'` to `&#39;`, see below.

```
@SpringBootApplication
open class WebApplication {

    private val TEMPLATE_MODE = "LOOSE_HTML5"

    @Bean
    open fun viewResolver() = ThymeleafViewResolver().apply {
        templateEngine = templateEngine()
    }

    @Bean
    open fun templateEngine() = SpringTemplateEngine().apply {
        templateResolvers = setOf(templateResolver())
        setDialect(CustomSpringDialect.Builder()
                .enableAngularJS1()
                .enableUIBootstrap()
                .toBeModified("my-attr")
                .toBeRenamed("my-tag")
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
```

## Sample

```
<div th:fragment="input_text(title,modelName,required,min,max)" th:remove="tag">
    <div class="form-group" th:ng-class="|{ 'has-error' : ${formName}.${modelName}.$invalid }|">
        <input type="text" class="form-control input-sm"
               th:ng-model="|form.${modelName}|" th:name="${modelName}"
               th:required="${required}" th:minlength="${min}" th:maxlength="${max}"
               th:popover-title="${title}" th:uib-popover-template="|'${modelName}Error.html'|"
               popover-trigger="'focus'" popover-placement="bottom-right"/>
        <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"
              th:ng-show="|${formName}.${modelName}.$invalid|"></span>
        <th:script type="text/ng-template" th:id="|${modelName}Error.html|">
            <ul class="list-unstyled">
                <li th:if="${required}"
                    th:ng-show="|${formName}.${modelName}.$error.required|">required</li>
                <li th:if="${min} != null"
                    th:ng-show="|${formName}.${modelName}.$error.minlength|" th:text="|greater than ${min}|"></li>
                <li th:if="${max} != null"
                    th:ng-show="|${formName}.${modelName}.$error.maxlength|" th:text="|less than ${max}|"></li>
            </ul>
        </th:script>
    </div>
</div>
```

## List of prepared attributes

[SupportAttributes.kt](https://github.com/nosix/thymeleaf-dialect/blob/master/src/main/kotlin/org/musyozoku/thymeleaf/dialect/SupportAttributes.kt)

## Extensions

[BuilderExtensions.kt](https://github.com/nosix/thymeleaf-dialect/blob/master/src/main/kotlin/org/musyozoku/thymeleaf/dialect/BuilderExtensions.kt)

- enableAngularJS1()
    - add ANGULAR_JS_1 attributes
    - add `script` tag
- enableUIBootstrap()
    - add UI_BOOTSTRAP attributes

