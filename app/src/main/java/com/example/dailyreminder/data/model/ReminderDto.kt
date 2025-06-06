import java.io.Serializable

data class ReminderDto(
    val id: Int?,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val completed: Boolean
) : Serializable
