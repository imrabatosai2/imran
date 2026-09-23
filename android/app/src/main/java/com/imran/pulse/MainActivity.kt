package com.imran.pulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PulseApp() }
    }
}

data class PulseVideo(
    val creator: String,
    val handle: String,
    val caption: String,
    val song: String,
    val likes: String,
    val comments: String,
    val colors: List<Color>
)

private val videos = listOf(
    PulseVideo("Maya Chen", "@mayachen", "Golden hour never misses ✨", "original sound · Maya Chen", "1.2M", "8,421", listOf(Color(0xFF301B3F), Color(0xFFE27D60))),
    PulseVideo("Jordan Miles", "@jmiles", "POV: you found the perfect weekend spot", "Sunset Lover · Petit Biscuit", "842K", "2,103", listOf(Color(0xFF12343B), Color(0xFFC89666))),
    PulseVideo("Aisha Rose", "@aisharose", "Three easy moves anyone can learn 💃", "Dance the Night · Dua Lipa", "2.4M", "14,082", listOf(Color(0xFF4B1248), Color(0xFFF80759))),
    PulseVideo("Noah Park", "@noahparks", "Tiny adventures, big memories.", "Bloom · The Paper Kites", "568K", "1,992", listOf(Color(0xFF134E5E), Color(0xFF71B280)))
)

@Composable
fun PulseApp() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize(), color = Color.Black) {
            var selectedTab by remember { mutableStateOf(0) }
            if (selectedTab == 0) FeedScreen() else PlaceholderScreen(selectedTab) 
            BottomNav(selectedTab) { selectedTab = it }
        }
    }
}

@Composable
private fun FeedScreen() {
    val pagerState = rememberPagerState(pageCount = { videos.size })
    var searchOpen by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        VerticalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page -> VideoPage(videos[page]) }
        Row(
            Modifier.align(Alignment.TopCenter).fillMaxWidth().padding(top = 22.dp, start = 20.dp, end = 12.dp),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Following", color = Color.White.copy(alpha = .65f), fontSize = 16.sp)
            Text("  For You", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { searchOpen = !searchOpen }, modifier = Modifier.padding(start = 54.dp)) {
                Icon(Icons.Default.Search, "Search", tint = Color.White)
            }
        }
        if (searchOpen) {
            Surface(Modifier.align(Alignment.TopCenter).fillMaxWidth().padding(top = 72.dp, start = 18.dp, end = 18.dp), shape = RoundedCornerShape(24.dp), color = Color.White) {
                Text("Search creators, sounds, or topics", Modifier.padding(16.dp), color = Color.Gray)
            }
        }
    }
}

@Composable
private fun VideoPage(video: PulseVideo) {
    var liked by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize().background(Brush.linearGradient(video.colors))) {
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = .8f)))))
        Column(Modifier.align(Alignment.BottomStart).padding(start = 18.dp, end = 82.dp, bottom = 82.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(38.dp).clip(CircleShape).background(Color.White.copy(alpha = .9f)), contentAlignment = Alignment.Center) { Text(video.creator.take(1), color = Color.Black, fontWeight = FontWeight.Bold) }
                Spacer(Modifier.width(10.dp))
                Text(video.handle, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(10.dp))
                Text("Follow", color = Color(0xFFFF2D55), fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(12.dp))
            Text(video.caption, color = Color.White, fontSize = 16.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("♫", color = Color.White, fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text(video.song, color = Color.White, fontSize = 13.sp)
            }
        }
        Column(Modifier.align(Alignment.BottomEnd).padding(end = 14.dp, bottom = 88.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { liked = !liked }) { Icon(if (liked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder, "Like", tint = if (liked) Color(0xFFFF2D55) else Color.White, modifier = Modifier.size(34.dp)) }
            Text(video.likes, color = Color.White, fontSize = 12.sp)
            Spacer(Modifier.height(14.dp))
            Icon(Icons.Default.ChatBubble, "Comments", tint = Color.White, modifier = Modifier.size(30.dp))
            Text(video.comments, color = Color.White, fontSize = 12.sp)
            Spacer(Modifier.height(14.dp))
            Icon(Icons.Default.Share, "Share", tint = Color.White, modifier = Modifier.size(30.dp))
            Text("Share", color = Color.White, fontSize = 12.sp)
        }
    }
}

@Composable
private fun BottomNav(selected: Int, onSelect: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        NavItem(Icons.Default.Home, "Home", selected == 0) { onSelect(0) }
        NavItem(Icons.Default.Search, "Discover", selected == 1) { onSelect(1) }
        IconButton(onClick = { }) { Icon(Icons.Default.Add, "Create", tint = Color.White, modifier = Modifier.size(32.dp)) }
        NavItem(Icons.Default.Favorite, "Inbox", selected == 2) { onSelect(2) }
        NavItem(Icons.Default.Person, "Profile", selected == 3) { onSelect(3) }
    }
}

@Composable
private fun NavItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, active: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) { Icon(icon, label, tint = if (active) Color.White else Color.Gray) }
        Text(label, color = if (active) Color.White else Color.Gray, fontSize = 10.sp)
    }
}

@Composable
private fun PlaceholderScreen(tab: Int) {
    Box(Modifier.fillMaxSize().background(Color(0xFF101010)), contentAlignment = Alignment.Center) {
        Text(when (tab) { 1 -> "Discover"; 2 -> "Inbox"; else -> "Profile" }, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}
