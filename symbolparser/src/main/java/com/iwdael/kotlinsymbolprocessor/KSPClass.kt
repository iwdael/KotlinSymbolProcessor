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
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import kotlin.reflect.KClass

/**
 * @author iwdael
 * @since 2024/8/19
 * @desc this is KSPClass
 */
val KSDeclaration.asKspClass: KSPClass
    get() {
        return KSPClass(this as KSClassDeclaration)
    }
val KSAnnotated.asKspClass: KSPClass
    get() {
        return KSPClass(this as KSClassDeclaration)
    }

class KSPClass(@Transient val ksp: KSClassDeclaration) {
    val isPublic by lazy { ksp.isPublic() }
    val isProtected by lazy { ksp.isProtected() }
    val isInternal by lazy { ksp.isInternal() }
    val isPrivate by lazy { ksp.isPrivate() }
    val isJavaPackagePrivate by lazy { ksp.isJavaPackagePrivate() }
    val simpleName by lazy { ksp.simpleName.getShortName() }
    val qualifierName by lazy { if (kspPackage.name.isEmpty()) simpleName else "${kspPackage.name}.${simpleName}" }
    val kspPackage by lazy { ksp.packageName.asKspPackage }
    val kspAnnotations by lazy { ksp.annotations.map { it.asKspAnnotation } }
    val kspProperties by lazy { ksp.getAllProperties().map { it.asKspProperty }.toList() }
    val kspFunctions by lazy { ksp.getAllFunctions().map { it.asKspFunction }.toList() }

    fun <T : Annotation> propertiesByAnnotation(kClass: KClass<T>): List<KSPProperty> {
        return kspProperties.filter { it.isAnnotationPresent(kClass) }
    }

    fun <T : Annotation> functionsByAnnotation(kClass: KClass<T>): List<KSPFunction> {
        return kspFunctions.filter { it.isAnnotationPresent(kClass) }
    }

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