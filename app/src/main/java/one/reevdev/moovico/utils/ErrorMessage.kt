package one.reevdev.moovico.utils

import androidx.annotation.StringRes
import one.reevdev.moovico.R

data class ErrorMessage(
    @StringRes val message: Int = R.string.message_generic_error,
    val throwableMessage: String? = null
)
