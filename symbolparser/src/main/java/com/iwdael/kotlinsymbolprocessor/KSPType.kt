package com.iwdael.kotlinsymbolprocessor

import com.google.devtools.ksp.symbol.KSTypeReference

/**
 * @author iwdael
 * @since 2024/8/20
 * @desc this is KSPType
 */
val KSTypeReference.asKspType: KSPType
    get() {
        return KSPType(this)
    }

class KSPType(@Transient val ksp: KSTypeReference) {
    val simpleName by lazy { ksp.resolve().declaration.simpleName.asString() }
    val qualifiedName by lazy { ksp.resolve().declaration.qualifiedName?.asString() }
    val types by lazy { ksp.resolve().arguments.map { it.type?.asKspType } }
}