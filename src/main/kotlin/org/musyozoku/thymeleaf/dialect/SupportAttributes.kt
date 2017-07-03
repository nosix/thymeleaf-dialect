package org.musyozoku.thymeleaf.dialect

object SupportAttributes {

    /**
     * AngularJS 1 attributes
     */
    val ANGULAR_JS_1 = arrayOf(
            // TODO: add attributes
            "ng-change", "ng-class", "ng-click",
            "ng-disabled",
            "ng-init",
            "ng-model",
            "ng-repeat",
            "ng-show",
            "ng-value")

    /**
     * UI Bootstrap attributes
     */
    val UI_BOOTSTRAP = arrayOf(
            // TODO: add attributes
            "is-open",
            "dismiss-on-timeout",
            "minlength",
            "popover-title",
            "typeahead-loading", "typeahead-no-results", "typeahead-on-select",
            "uib-datepicker-popup", "uib-popover-template", "uib-tooltip", "uib-typeahead")
}