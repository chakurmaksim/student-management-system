package com.epam.ahnl.service.exception

import java.lang.RuntimeException

class StudentNotFoundException(message: String) : RuntimeException(message) {
}