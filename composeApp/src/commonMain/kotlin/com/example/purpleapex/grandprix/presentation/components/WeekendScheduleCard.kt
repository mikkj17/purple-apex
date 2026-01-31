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
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(12.dp),
        ) {
            Text(
                text = "Weekend schedule",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            schedule.firstPractice?.let { SessionRow("Practice 1", it) }
            schedule.secondPractice?.let { SessionRow("Practice 2", it) }
            schedule.thirdPractice?.let { SessionRow("Practice 3", it) }
            schedule.sprintQualifying?.let { SessionRow("Sprint Qualifying", it) }
            schedule.sprint?.let { SessionRow("Sprint", it) }
            schedule.qualifying?.let { SessionRow("Qualifying", it) }
            SessionRow("Race", Session(schedule.date, schedule.time), includeDivider = false)
        }
    }
}

@Composable
private fun SessionRow(name: String, session: Session, includeDivider: Boolean = true) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
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
        if (includeDivider)
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}
