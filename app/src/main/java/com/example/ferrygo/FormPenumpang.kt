package com.example.ferrygo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Anggota 2: formulir masukan, validasi, dan pesan kesalahan

// Pilihan kelas berbentuk kartu (menggantikan radio button biasa)
@Composable
fun KartuKelas(
    kelas: KelasLayanan,
    terpilih: Boolean,
    aktif: Boolean,
    onPilih: () -> Unit,
    modifier: Modifier = Modifier
) {
    val warnaLatar =
        if (terpilih) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val garis = if (terpilih) {
        BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    } else {
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    }
    Card(
        modifier = modifier
            .alpha(if (aktif) 1f else 0.6f)
            .selectable(
                selected = terpilih,
                enabled = aktif,
                role = Role.RadioButton,
                onClick = onPilih
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = warnaLatar),
        border = garis
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(stringResource(kelas.labelRes), fontWeight = FontWeight.Bold)
            Text(
                text = rupiah(kelas.tarif),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun OpsiMotor(
    dipilih: Boolean,
    aktif: Boolean,
    onUbah: (Boolean) -> Unit
) {
    KartuPutih {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = dipilih,
                    enabled = aktif,
                    role = Role.Checkbox,
                    onValueChange = onUbah
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = dipilih, onCheckedChange = null, enabled = aktif)
            Text(
                text = stringResource(R.string.opsi_motor, rupiah(TARIF_MOTOR)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }
    }
}
