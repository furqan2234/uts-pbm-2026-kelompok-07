package com.example.ferrygo

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Anggota 4: panel ringkasan (jumlah per kategori, rincian tarif, total)

// Ringkasan: jumlah per kategori, rincian tarif, dan total (otomatis mengikuti isi daftar)
@Composable
fun PanelRingkasan(
    @StringRes judulRes: Int,
    daftar: List<Penumpang>,
    denganMotor: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(judulRes),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                hitungPerKategori(daftar).forEach { (kategori, jumlah) ->
                    KotakKategori(kategori, jumlah, Modifier.weight(1f))
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            Text(stringResource(R.string.rincian_tarif), fontWeight = FontWeight.Bold)
            daftar.forEach { p ->
                BarisTarif(
                    label = stringResource(
                        R.string.label_dengan_keterangan,
                        p.nama,
                        stringResource(tentukanKategori(p.usia).labelRes)
                    ),
                    nilai = rupiah(hitungTarif(p))
                )
            }
            if (denganMotor) {
                BarisTarif(stringResource(R.string.biaya_motor), rupiah(TARIF_MOTOR))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.total),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = rupiah(hitungTotal(daftar, denganMotor)),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun KotakKategori(
    kategori: KategoriPenumpang,
    jumlah: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = jumlah.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = warnaKategori(kategori)
        )
        Text(
            text = stringResource(kategori.labelRes),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1
        )
    }
}

@Composable
fun BarisTarif(label: String, nilai: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, modifier = Modifier.weight(1f))
        Text(text = nilai, modifier = Modifier.padding(start = 8.dp))
    }
}
