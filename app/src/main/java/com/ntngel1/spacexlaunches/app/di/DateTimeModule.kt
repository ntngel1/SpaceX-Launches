package com.ntngel1.spacexlaunches.app.di

import dagger.Module
import dagger.Provides
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import javax.inject.Singleton

@Module
class DateTimeModule {

    @Provides
    @Singleton
    fun provideDateTimeFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
}