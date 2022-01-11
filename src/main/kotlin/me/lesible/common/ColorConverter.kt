package me.lesible.common

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.color.ANSIConstants.*
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase

class ColorConverter : ForegroundCompositeConverterBase<ILoggingEvent?>() {

    override fun getForegroundColorCode(event: ILoggingEvent?): String {
        val level: Level? = event?.level
        return when (level?.toInt()) {
            Level.ERROR_INT -> RED_FG
            Level.WARN_INT -> YELLOW_FG
            else -> GREEN_FG
        }
    }

}