package org.anyspirit.thymeleaf.dialect

fun CustomSpringDialect.Builder.enableAngularJS1() = apply {
    toBeModified(*SupportAttributes.ANGULAR_JS_1)
    toBeRenamed("script")
}

fun CustomSpringDialect.Builder.enableUIBootstrap() = apply {
    toBeModified(*SupportAttributes.UI_BOOTSTRAP)
}
