package com.example.ferrygo

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Anggota 5: pengelolaan state dan alur antar-bagian layar

@Composable
fun TiketKapalScreen() {
    // state pemesanan
    var daftar by remember { mutableStateOf(listOf<Penumpang>()) }
    var denganMotor by remember { mutableStateOf(false) }
    var terkunci by remember { mutableStateOf(false) }

    // state formulir
    var nama by remember { mutableStateOf("") }
    var usiaTeks by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf(KelasLayanan.EKONOMI) }

    // turunan state (dihitung ulang otomatis saat state berubah)
    val usia = parseUsia(usiaTeks)
    val usiaError = usiaTeks.isNotEmpty() && usia == null
    val namaError = nama.isNotEmpty() && nama.isBlank()
    val penuh = daftar.size >= MAKS_PENUMPANG
    val bisaTambah = !terkunci && !penuh && nama.isNotBlank() && usia != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BannerJudul()

        if (terkunci) {
            LabelStatus(pesanRes = R.string.status_terkonfirmasi)
        }

        FormPenumpang(
            nama = nama,
            usiaTeks = usiaTeks,
            kelas = kelas,
            namaError = namaError,
            usiaError = usiaError,
            aktif = !terkunci,
            bisaTambah = bisaTambah,
            onNamaChange = { nama = it },
            onUsiaChange = { usiaTeks = it },
            onKelasChange = { kelas = it },
            onTambah = {
                if (usia != null) {
                    daftar = daftar + Penumpang(nama.trim(), usia, kelas) // buat daftar baru
                    nama = ""        // kosongkan formulir setelah entri ditambahkan
                    usiaTeks = ""
                }
            }
        )

        if (penuh && !terkunci) {
            Text(
                text = stringResource(R.string.pesan_penuh, MAKS_PENUMPANG),
                color = MaterialTheme.colorScheme.error
            )
        }

        OpsiMotor(
            dipilih = denganMotor,
            aktif = !terkunci,
            onUbah = { denganMotor = it }
        )

        Text(
            text = stringResource(R.string.judul_daftar, daftar.size, MAKS_PENUMPANG),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (daftar.isEmpty()) {
                KeadaanKosong()
            } else {
                daftar.forEachIndexed { index, p ->
                    KartuPenumpang(
                        penumpang = p,
                        bisaHapus = !terkunci,
                        // hapus berdasarkan indeks agar penumpang dengan data sama tidak ikut terhapus
                        onHapus = { daftar = daftar.filterIndexed { i, _ -> i != index } }
                    )
                }
            }
        }

        PanelRingkasan(
            judulRes = if (terkunci) R.string.ringkasan_final else R.string.ringkasan_judul,
            daftar = daftar,
            denganMotor = denganMotor
        )

        if (daftar.isNotEmpty() && !adaDewasa(daftar)) {
            Text(
                text = stringResource(R.string.syarat_dewasa),
                color = MaterialTheme.colorScheme.error
            )
        }

        if (terkunci) {
            // Pemesanan Baru: kosongkan seluruh data
            Button(
                onClick = {
                    daftar = emptyList()
                    denganMotor = false
                    terkunci = false
                    nama = ""
                    usiaTeks = ""
                    kelas = KelasLayanan.EKONOMI
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) { Text(stringResource(R.string.pemesanan_baru)) }
        } else {
            // Konfirmasi hanya aktif bila syarat pemesanan terpenuhi
            Button(
                onClick = { terkunci = true },
                enabled = bolehKonfirmasi(daftar),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) { Text(stringResource(R.string.konfirmasi)) }
        }
    }
}
