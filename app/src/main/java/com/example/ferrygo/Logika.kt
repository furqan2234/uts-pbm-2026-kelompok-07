package com.example.ferrygo

// Seluruh aturan perhitungan dan keputusan ditulis di sini, terpisah dari composable.

const val MAKS_PENUMPANG = 8
const val TARIF_MOTOR = 150_000
const val USIA_MAKS = 120

// Kategori ditentukan dari usia
fun tentukanKategori(usia: Int): KategoriPenumpang = when {
    usia < 2 -> KategoriPenumpang.BAYI
    usia in 2..11 -> KategoriPenumpang.ANAK
    usia in 12..59 -> KategoriPenumpang.DEWASA
    else -> KategoriPenumpang.LANSIA
}

// Persentase tarif per kategori
fun persenTarif(kategori: KategoriPenumpang): Int = when (kategori) {
    KategoriPenumpang.BAYI -> 0
    KategoriPenumpang.ANAK -> 50
    KategoriPenumpang.DEWASA -> 100
    KategoriPenumpang.LANSIA -> 75
}

// Tarif satu penumpang = tarif kelas x persen kategori
fun hitungTarif(p: Penumpang): Int =
    p.kelas.tarif * persenTarif(tentukanKategori(p.usia)) / 100

// Total = jumlah tarif semua penumpang + biaya motor (bila dipilih)
fun hitungTotal(daftar: List<Penumpang>, denganMotor: Boolean): Int {
    val tarifMotor = if (denganMotor) TARIF_MOTOR else 0
    return daftar.sumOf { hitungTarif(it) } + tarifMotor
}

// Jumlah penumpang per kategori (kategori tanpa penumpang bernilai 0)
fun hitungPerKategori(daftar: List<Penumpang>): Map<KategoriPenumpang, Int> =
    KategoriPenumpang.entries.associateWith { kategori ->
        daftar.count { tentukanKategori(it.usia) == kategori }
    }

// Minimal ada satu penumpang dewasa
fun adaDewasa(daftar: List<Penumpang>): Boolean =
    daftar.any { tentukanKategori(it.usia) == KategoriPenumpang.DEWASA }

// Syarat pemesanan: 1-8 penumpang dan minimal 1 dewasa
fun bolehKonfirmasi(daftar: List<Penumpang>): Boolean =
    daftar.size in 1..MAKS_PENUMPANG && adaDewasa(daftar)

// Aman dari masukan kosong, huruf, dan angka di luar batas (hasil null bila tidak valid)
fun parseUsia(teks: String): Int? =
    teks.trim().toIntOrNull()?.takeIf { it in 0..USIA_MAKS }

// 1500000 -> "1.500.000" (awalan "Rp" ditambahkan dari strings.xml)
fun formatRibuan(nilai: Int): String =
    nilai.toString().reversed().chunked(3).joinToString(".").reversed()
