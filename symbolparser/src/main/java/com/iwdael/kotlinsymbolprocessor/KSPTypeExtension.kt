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
    return ksp.toTypeName()
}