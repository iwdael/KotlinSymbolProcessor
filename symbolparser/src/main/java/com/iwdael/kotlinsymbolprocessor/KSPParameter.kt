package com.iwdael.kotlinsymbolprocessor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSValueParameter
import kotlin.reflect.KClass

/**
 * @author iwdael
 * @since 2024/8/19
 * @desc this is KSPParameter
 */
val KSAnnotated.asKspParameter: KSPParameter
    get() {
        return KSPParameter(this as KSValueParameter)
    }

class KSPParameter(@Transient val ksp: KSValueParameter) {
    val name by lazy { ksp.name?.getShortName() ?: "" }
    val type by lazy { ksp.type.asKspType }
    val kspAnnotations by lazy { ksp.annotations.map { it.asKspAnnotation }.toList() }
    val kspFunction by lazy { ksp.parent?.asKspFunction }

    @OptIn(KspExperimental::class)
    fun <T : Annotation> annotations(kClass: KClass<T>): List<T> {
        return ksp.getAnnotationsByType(kClass).toList()
    }
    @OptIn(KspExperimental::class)
    fun <T : Annotation> annotation(kClass: KClass<T>): T? {
        return ksp.getAnnotationsByType(kClass).firstOrNull()
    }
    @OptIn(KspExperimental::class)
    fun <T : Annotation> isAnnotationPresent(kClass: KClass<T>): Boolean {
        return ksp.isAnnotationPresent(kClass)
    }
}