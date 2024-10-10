package com.iwdael.kotlinsymbolprocessor

import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName

/**
 * @author iwdael
 * @since 2024/8/22
 * @desc this is KSPTypeExtension
 */
fun KSPType.asTypeName(): TypeName {
    return try {
        if (types.isEmpty()) {
            ksp.resolve().toClassName().copy(ksp.resolve().isMarkedNullable)
        } else {
            ksp.resolve().toClassName()
                .let {
                    it.copy(ksp.resolve().isMarkedNullable, it.annotations, it.tags)
                }
                .parameterizedBy(types.map { it!!.asTypeName() })
        }
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}