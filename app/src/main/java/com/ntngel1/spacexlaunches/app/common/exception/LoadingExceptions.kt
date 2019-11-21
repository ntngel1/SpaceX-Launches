package com.ntngel1.spacexlaunches.app.common.exception

class AlreadyLoadingException(
    message: String = "Loading was already started!"
) : Exception(message)

class AlreadyLoadedException(
    message: String = "All data was already loaded!"
) : Exception(message)