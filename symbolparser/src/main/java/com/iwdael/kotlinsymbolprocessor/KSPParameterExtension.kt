package com.iwdael.kotlinsymbolprocessor

import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ksp.toClassName

/**
 * @author iwdael
 * @since 2024/8/21
 * @desc this is PoetParameter
 */


fun KSPParameter.asParameter() = ParameterSpec.builder(name, type.asTypeName()).build()