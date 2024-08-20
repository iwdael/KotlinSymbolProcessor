package com.iwdael.kotlinsymbolprocessor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.isInternal
import com.google.devtools.ksp.isJavaPackagePrivate
import com.google.devtools.ksp.isPrivate
import com.google.devtools.ksp.isProtected
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import kotlin.reflect.KClass

/**
 * @author iwdael
 * @since 2024/8/19
 * @desc this is KSPFunction
 */
val KSDeclaration.asKspFunction: KSPFunction
    get() {
        return KSPFunction(this as KSFunctionDeclaration)
    }
val KSAnnotated.asKspFunction: KSPFunction
    get() {
        return KSPFunction(this as KSFunctionDeclaration)
    }

val KSNode.asKspFunction: KSPFunction
    get() {
        return KSPFunction(this as KSFunctionDeclaration)
    }

class KSPFunction(@Transient val ksp: KSFunctionDeclaration) {
    val isPublic by lazy { ksp.isPublic() }
    val isProtected by lazy { ksp.isProtected() }
    val isInternal by lazy { ksp.isInternal() }
    val isPrivate by lazy { ksp.isPrivate() }
    val isJavaPackagePrivate by lazy { ksp.isJavaPackagePrivate() }
    val name by lazy { ksp.simpleName.getShortName() }
    val qualifierName by lazy { "${kspClass?.qualifierName}#${name}" }
    val returnType by lazy { ksp.returnType?.asKspType }
    val modifiers by lazy { ksp.modifiers }
    val kspParameters by lazy { ksp.parameters.map { it.asKspParameter }.toList() }
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