package com.epam.ahnl.service.exception

import java.lang.RuntimeException

class StudentNotFoundException: RuntimeException {
    constructor(): super()
    constructor(message: String): super(message)
    constructor(cause: Throwable): super(cause)
    constructor(message: String, cause: Throwable): super(message, cause)
}