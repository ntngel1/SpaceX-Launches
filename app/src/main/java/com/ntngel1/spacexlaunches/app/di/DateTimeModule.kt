package com.ntngel1.spacexlaunches.app.di

import dagger.Module
import dagger.Provides
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Singleton

@Module
class DateTimeModule {

    @Provides
    @Singleton
    fun provideDateTimeFormatter(): DateTimeFormatter {
        // TODO
        return DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm")
    }
}