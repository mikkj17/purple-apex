package com.example.purpleapex.grandprix.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.purpleapex.core.presentation.components.AppCard
import com.example.purpleapex.schedule.domain.ScheduleDetail
import com.example.purpleapex.schedule.domain.Session

@Composable
fun WeekendScheduleCard(schedule: ScheduleDetail, modifier: Modifier = Modifier) {
    AppCard(
        shape = MaterialTheme.shapes.small,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "WEEKEND SCHEDULE",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            SessionRow("Practice 1", schedule.firstPractice)
            SessionRow("Practice 2", schedule.secondPractice)
            schedule.thirdPractice?.let { SessionRow("Practice 3", it) }
            schedule.sprintQualifying?.let { SessionRow("Sprint Qualifying", it) }
            schedule.sprint?.let { SessionRow("Sprint", it) }
            SessionRow("Qualifying", schedule.qualifying)
            SessionRow("Race", Session(schedule.date, schedule.time))
        }
    }
}

@Composable
private fun SessionRow(name: String, session: Session?) {
    if (session == null) return
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = session.date,
                    style = MaterialTheme.typography.bodySmall
                )
                session.time?.let {
                    Text(
                        text = it.dropLast(1),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}
