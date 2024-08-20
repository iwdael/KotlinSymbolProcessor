package com.iwdael.kotlinsymbolprocessor

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSNode
import kotlin.reflect.KClass

/**
 * @author iwdael
 * @since 2024/8/19
 * @desc this is KSPAnnotation
 */
val KSNode.asKspAnnotation: KSPAnnotation
    get() {
        return KSPAnnotation(this as KSAnnotation)
    }

class KSPAnnotation(@Transient val ksp: KSAnnotation) {
    val simpleName by lazy { ksp.shortName.getShortName() }
    val qualifierName by lazy { ksp.annotationType.resolve().declaration.qualifiedName?.asString() }
}

