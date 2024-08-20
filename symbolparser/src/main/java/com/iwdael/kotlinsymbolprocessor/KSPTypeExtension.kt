package com.iwdael.kotlinsymbolprocessor

import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ksp.toClassName

/**
 * @author iwdael
 * @since 2024/8/22
 * @desc this is KSPTypeExtension
 */
fun KSPType.asTypeName(): TypeName {
    return try {
        if (types.isEmpty()) {
            ksp.resolve().toClassName()
        } else {
            ksp.resolve().toClassName().parameterizedBy(types.map { it!!.asTypeName() })
        }
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}