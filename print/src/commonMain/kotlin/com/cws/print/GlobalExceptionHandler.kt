package com.cws.print

expect fun GlobalExceptionHandler(context: Context, block: (Throwable) -> Unit)