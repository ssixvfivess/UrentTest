package com.vyatkina.urenttest.data.service

sealed interface CityServiceResult<out T> {

    data class Success<T>(val data: T) : CityServiceResult<T>

    data class Error(val exception: CityServiceException) : CityServiceResult<Nothing>
}

sealed class CityServiceException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {

    class Network(cause: Throwable) :
        CityServiceException("Проблема с сетью при загрузке городов", cause)

    class Client(cause: Throwable) :
        CityServiceException("Некорректный запрос на получение городов", cause)

    class Server(cause: Throwable) :
        CityServiceException("Сервер временно недоступен", cause)

    class Serialization(cause: Throwable) :
        CityServiceException("Не удалось обработать ответ сервера", cause)

    class Unknown(cause: Throwable) :
        CityServiceException("Неизвестная ошибка при загрузке городов", cause)
}
