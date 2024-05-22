import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun completeWeekOfADay(date: LocalDate): Array<LocalDate?> {
    val dayOfWeek = date.dayOfWeek.value
    val week = Array<LocalDate?>(5) {null}
    for (dayInt in 1..5) {
        if (dayInt<=dayOfWeek) {
            week[dayInt-1] = date.minusDays((dayOfWeek-dayInt).toLong())
        } else {
            week[dayInt-1] = date.plusDays((dayInt-dayOfWeek).toLong())
        }
    }
    return week
}