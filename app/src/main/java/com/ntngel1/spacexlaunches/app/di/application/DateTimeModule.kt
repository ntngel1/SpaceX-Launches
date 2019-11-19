package com.ntngel1.spacexlaunches.app.di.application

import dagger.Module
import dagger.Provides
import org.threeten.bp.format.DateTimeFormatter

@Module
class DateTimeModule {

    @Provides
    @AppScope
    fun provideDateTimeFormatter(): DateTimeFormatter {
        // TODO
        return DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm")
    }
}