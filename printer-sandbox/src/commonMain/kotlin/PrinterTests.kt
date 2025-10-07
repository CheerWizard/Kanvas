import com.cws.printer.LogLevel
import com.cws.printer.Printer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

object PrinterTests {

    private const val TAG = "PrinterTests"

    val logTags = listOf("Auth", "Network", "Database", "Cache", "UI", "App", "Profiler", "Service")

    val verboseMessages = listOf(
        "Start profiling function X", "End profiling function X",
        "Entering method Y", "Exiting method Y", "Loop iteration finished"
    )

    val debugMessages = listOf(
        "Checking credentials", "Cache hit for key", "Request headers: {Authorization=...}",
        "Response received", "Parsing JSON", "Updating state", "Coroutine started"
    )

    val infoMessages = listOf(
        "Application started", "User profile loaded", "Connected to database",
        "Service initialized", "Background task finished"
    )

    val warnMessages = listOf(
        "Token about to expire", "Response slow", "Cache miss", "Deprecated API usage",
        "Low memory warning"
    )

    val errorMessages = listOf(
        "Failed to fetch records", "Unhandled exception occurred", "Network unreachable",
        "Database connection failed", "Null pointer exception"
    )

    val fatalMessages = listOf(
        "FATAL error happened", "FATAL terminating process", "FATAL crash appeared",
        "FATAL on main", "FATAL shutdown main"
    )

    val fatalExceptions = listOf(
        IllegalArgumentException(), NullPointerException(), IllegalStateException(),
        ConcurrentModificationException(), IndexOutOfBoundsException()
    )

    private val scope = CoroutineScope(Dispatchers.Default)

    fun run(): Job {
        return scope.launch {
            while (isActive) {
                randomPrint()
                delay(1.seconds)
            }
        }
    }

    private fun randomPrint() {
        val random = (1..<LogLevel.entries.size).random()
        val level = LogLevel.entries[random]
        val tag = logTags.random()
        when (level) {
            LogLevel.NONE -> return
            LogLevel.VERBOSE -> {
                val message = verboseMessages.random()
                Printer.v(tag, message)
            }
            LogLevel.DEBUG -> {
                val message = debugMessages.random()
                Printer.d(tag, message)
            }
            LogLevel.INFO -> {
                val message = infoMessages.random()
                Printer.i(tag, message)
            }
            LogLevel.WARNING -> {
                val message = warnMessages.random()
                Printer.w(tag, message)
            }
            LogLevel.ERROR -> {
                val message = errorMessages.random()
                Printer.e(tag, message)
            }
            LogLevel.FATAL -> {
                val message = fatalMessages.random()
                val exception = fatalExceptions.random()
                Printer.e(tag, message, exception)
            }
        }
    }

}