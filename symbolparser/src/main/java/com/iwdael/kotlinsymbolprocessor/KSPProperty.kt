package com.iwdael.kotlinsymbolprocessor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.isInternal
import com.google.devtools.ksp.isJavaPackagePrivate
import com.google.devtools.ksp.isPrivate
import com.google.devtools.ksp.isProtected
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.symbol.Visibility
import kotlin.reflect.KClass

/**
 * @author iwdael
 * @since 2024/8/19
 * @desc this is KSPProperty
 */
val KSDeclaration.asKspProperty: KSPProperty
    get() {
        return KSPProperty(this as KSPropertyDeclaration)
    }
val KSAnnotated.asKspProperty: KSPProperty
    get() {
        return KSPProperty(this as KSPropertyDeclaration)
    }


class KSPProperty(@Transient val ksp: KSPropertyDeclaration) {
    val isPublic by lazy { ksp.isPublic() }
    val isProtected by lazy { ksp.isProtected() }
    val isInternal by lazy { ksp.isInternal() }
    val isPrivate by lazy { ksp.isPrivate() }
    val isJavaPackagePrivate by lazy { ksp.isJavaPackagePrivate() }
    val name by lazy { ksp.simpleName.getShortName() }
    val type by lazy { ksp.type.asKspType }
    val modifiers by lazy { ksp.modifiers.toList() }
    val kspAnnotations by lazy { ksp.annotations.map { it.asKspAnnotation }.toList() }
    val kspClass by lazy { ksp.parentDeclaration?.asKspClass }

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