package com.example.ferrygo

import androidx.annotation.StringRes

// Kelas layanan beserta tarif dasar dan label (label diambil dari strings.xml)
enum class KelasLayanan(val tarif: Int, @StringRes val labelRes: Int) {
    EKONOMI(120_000, R.string.kelas_ekonomi),
    EKSEKUTIF(200_000, R.string.kelas_eksekutif)
}

// Kategori penumpang yang ditentukan dari usia
enum class KategoriPenumpang(@StringRes val labelRes: Int) {
    BAYI(R.string.kategori_bayi),
    ANAK(R.string.kategori_anak),
    DEWASA(R.string.kategori_dewasa),
    LANSIA(R.string.kategori_lansia)
}

// Model data: setiap entri penumpang yang ditambahkan pengguna disimpan sebagai objek ini
data class Penumpang(
    val nama: String,
    val usia: Int,
    val kelas: KelasLayanan
)
