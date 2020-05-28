package com.epam.ahnl.controller.response

import java.time.LocalDateTime

class ResponseMessage(val timeStamp: LocalDateTime = LocalDateTime.now(), val message: String)