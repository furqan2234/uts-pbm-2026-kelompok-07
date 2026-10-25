package com.example.ferrygo

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Anggota 4: komponen tampilan yang dipakai bersama

// Format rupiah: awalan "Rp" diambil dari strings.xml
@Composable
fun rupiah(nilai: Int): String =
    stringResource(R.string.format_rupiah, formatRibuan(nilai))

// Warna lencana tiap kategori (hanya urusan tampilan, bukan aturan kasus)
fun warnaKategori(kategori: KategoriPenumpang): Color = when (kategori) {
    KategoriPenumpang.BAYI -> Color(0xFFD81B60)
    KategoriPenumpang.ANAK -> Color(0xFFEF6C00)
    KategoriPenumpang.DEWASA -> Color(0xFF1E88E5)
    KategoriPenumpang.LANSIA -> Color(0xFF2E7D32)
}

// Kartu putih berpojok membulat, dipakai ulang oleh banyak bagian
@Composable
fun KartuPutih(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        content = content
    )
}

@Composable
fun BannerJudul() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ikon_kapal),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = stringResource(R.string.judul_aplikasi),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = stringResource(R.string.subjudul_aplikasi),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}

@Composable
fun LabelStatus(@StringRes pesanRes: Int) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE8F5E9),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(pesanRes),
            color = Color(0xFF2E7D32),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
    }
}
