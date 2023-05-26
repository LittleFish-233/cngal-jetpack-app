package com.cngal.app.compose.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FiberNew
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cngal.app.compose.shared.TitleCard
import com.cngal.app.helper.appContext
import com.cngal.app.helper.openNewTabWindow
import com.cngal.app.model.home.AnnouncementModel

@Composable
fun AnnouncementGroupCard(model: List<AnnouncementModel>)
{
    val items =   model.take(6)


    TitleCard(title = "公告", link = "https://www.cngal.org/search?Types=Notice", content = {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            items.forEach { item ->
                AnnouncementCard(
                    model = item,
                    latest = model.indexOf(item) == model.count { it.priority > 0 },
                    onClickDetail = {
                        //todo 替换跳转页面
                        openNewTabWindow(
                            "https://www.cngal.org/${item.url}",
                            appContext
                        )
                    })
            }

        }
    })
}

@Composable
fun AnnouncementCard(model: AnnouncementModel, latest: Boolean, onClickDetail: () -> Unit)
{

    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (latest)
                    {
                        Icon(
                            Icons.Filled.FiberNew,
                            contentDescription = "最新",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    } else if (model.priority > 0)
                    {
                        Icon(
                            Icons.Filled.PushPin,
                            contentDescription = "置顶",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Text(
                        text = model.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (latest)
                        {
                            FontWeight.Bold
                        } else
                        {
                            FontWeight.Normal
                        }
                    )
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded)
                        {
                            "展开"
                        } else
                        {
                            "收缩"
                        }
                    )
                }
            }

            if (expanded)
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = model.briefIntroduction,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(0.dp, 12.dp, 12.dp, 12.dp),
                            onClick = { onClickDetail() }
                        ) {
                            Text(
                                text = "查看详情",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

            }
        }
    }

}