package com.example.bin.data.repository

import android.database.sqlite.SQLiteException
import com.example.bin.domain.model.ErrorType
import com.example.bin.domain.model.ResultBin
import retrofit2.HttpException
import java.net.UnknownHostException

interface ExceptionHandle {

    fun handle(exception: Exception?): ResultBin

    class Base : ExceptionHandle {
        override fun handle(exception: Exception?): ResultBin = ResultBin.Fail(
            when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.HTTP_EXCEPTION
                is SQLiteException -> ErrorType.SQL_EXCEPTION
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}
