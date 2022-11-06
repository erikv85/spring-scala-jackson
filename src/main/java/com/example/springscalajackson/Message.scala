package com.example.anotherscalajackson

import java.time.LocalDateTime
import scala.beans.BeanProperty

case class Message(
  @BeanProperty message: String,
  @BeanProperty maybeTime: Option[LocalDateTime]
)
