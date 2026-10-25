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
