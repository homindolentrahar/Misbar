package com.homindolentrahar.misbar.others.wrappers

enum class Status {
    Success, Loading, Error
}

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val status: Status
) {
    class Success<T>(data: T) : Resource<T>(data, null, Status.Success)
    class Loading<T>(data: T? = null) : Resource<T>(data, null, Status.Loading)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message, Status.Error)
}
