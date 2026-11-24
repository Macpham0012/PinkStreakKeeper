package com.pinkstreakkeeper

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ink = Color(0xFF2D1739)
private val pink = Color(0xFFFF65C7)
private val violet = Color(0xFFB38AFF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KeeperUI(this) }
    }
}

@Composable
fun KeeperUI(context: Context) {
    val prefs = context.getSharedPreferences("keeper", Context.MODE_PRIVATE)
    var people by remember { mutableStateOf(prefs.getString("people", "") ?: "") }
    var message by remember { mutableStateOf(prefs.getString("message", "✨ Chào bạn! Gửi một streak nha 💗") ?: "") }
    var interval by remember { mutableStateOf(prefs.getString("interval", "23") ?: "23") }
    var log by remember { mutableStateOf(prefs.getString("log", "Chào mừng đến Pink Streak Keeper ♡") ?: "") }
    var reminderEnabled by remember { mutableStateOf(prefs.getBoolean("reminder", false)) }

    MaterialTheme(colorScheme = lightColorScheme(primary = pink, secondary = violet, background = Color(0xFFFFF4FC))) {
        Surface(color = Color(0xFFFFF4FC), modifier = Modifier.fillMaxSize()) {
            LazyColumn(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                item {
                    Text("💗 Pink Streak Keeper", fontSize = 27.sp, color = ink)
                    Text("Bản Android prototype · chuẩn bị và nhắc thao tác thủ công", color = violet)
                }
                item { Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE2F4))) {
                    Column(Modifier.padding(16.dp)) {
                        Text("🌸 Danh sách bạn bè / người nhận", color = ink)
                        Text("Mỗi dòng là một tên hoặc ghi chú; không tự gửi tin.")
                        Field(people, "Tên, mỗi dòng một người", { people = it; prefs.edit().putString("people",it).apply() })
                    }
                } }
                item { Card {
                    Column(Modifier.padding(16.dp)) {
                        Text("💌 Mẫu tin nhắn", color = ink)
                        Field(message, "Nhập tin nhắn...", { message = it; prefs.edit().putString("message",it).apply() })
                        Button(onClick = {
                            val clip = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                            clip.setPrimaryClip(android.content.ClipData.newPlainText("streak",message))
                            Toast.makeText(context,"Đã sao chép mẫu",Toast.LENGTH_SHORT).show()
                            log = "Đã sao chép mẫu • " + java.text.SimpleDateFormat("HH:mm",java.util.Locale.getDefault()).format(java.util.Date()) + "\n" + log
                            prefs.edit().putString("log",log).apply()
                        }) { Text("📋 Sao chép mẫu") }
                    }
                } }
                item { Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE2FF))) {
                    Column(Modifier.padding(16.dp)) {
                        Text("⏰ Chu kỳ nhắc", color = ink)
                        Field(interval, "Số giờ (ví dụ 23)", { interval = it.filter(Char::isDigit); prefs.edit().putString("interval",interval).apply() })
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Switch(reminderEnabled, onCheckedChange = { reminderEnabled = it; prefs.edit().putBoolean("reminder",it).apply() })
                            Text(if(reminderEnabled) "Nhắc đang bật (prototype)" else "Bật trạng thái nhắc (prototype)")
                        }
                        Text("Prototype này lưu cấu hình nhắc; lịch nền/notification chưa tích hợp.")
                    }
                } }
                item {
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/"))
                        context.startActivity(intent)
                    }) { Text("🎵 Mở TikTok để thao tác thủ công") }
                }
                item { Card {
                    Column(Modifier.padding(16.dp)) {
                        Text("📝 Nhật ký", color = ink)
                        Text(log, color = ink)
                        Button(onClick = { log = ""; prefs.edit().putString("log","").apply() }) { Text("Xóa nhật ký") }
                    }
                } }
                item { Text("🔒 Dữ liệu được lưu cục bộ trong SharedPreferences. Không tích hợp tự động gửi tin hay đăng nhập TikTok.", color = ink) }
            }
        }
    }
}

@Composable
fun Field(value: String, hint: String, onChange: (String)->Unit) {
    Column(Modifier.fillMaxWidth().padding(vertical=8.dp)) {
        BasicTextField(value, onChange, modifier=Modifier.fillMaxWidth(), decorationBox = { inner ->
            if(value.isEmpty()) Text(hint, color=Color.Gray)
            inner()
        })
    }
}
