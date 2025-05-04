package dev.jianastrero.utils.log

/**
 * Defines the severity levels for logging.
 *
 * This enum provides a hierarchical set of log levels that can be used to
 * filter log messages based on their importance.
 *
 * @property level The numeric value representing the severity level (higher values = more severe)
 */
enum class LogLevel(val level: Int) {
    /**
     * Debug level for detailed information, typically useful during development.
     */
    DEBUG(0),

    /**
     * Info level for general information about application progress.
     */
    INFO(1),

    /**
     * Warning level for potentially harmful situations that don't prevent the application from working.
     */
    WARNING(2),

    /**
     * Error level for error events that might still allow the application to continue running.
     */
    ERROR(3),
}
