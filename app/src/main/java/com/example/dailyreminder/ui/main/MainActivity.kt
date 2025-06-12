package com.example.dailyreminder.ui.main

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyreminder.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var tvKegiatan: TextView
    private lateinit var lvKegiatan: ListView
    private lateinit var btnAddNote: ImageButton
    private lateinit var imgAccount: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi view
        calendarView = findViewById(R.id.calendarView)
        tvKegiatan = findViewById(R.id.tvKegiatan)
        lvKegiatan = findViewById(R.id.lvKegiatan)
        btnAddNote = findViewById(R.id.btnAddNote)
        imgAccount = findViewById(R.id.imgAccount)

        // Set kegiatan default
        setKegiatanHariIni(getCurrentDate())

        // Ubah kegiatan berdasarkan tanggal dipilih
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            setKegiatanHariIni(selectedDate)
        }

        // Tambah Note (nanti bisa diarahkan ke AddNoteActivity)
        btnAddNote.setOnClickListener {
            Toast.makeText(this, "Tambah catatan diklik", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, AddNoteActivity::class.java))
        }

        // Klik gambar akun
        imgAccount.setOnClickListener {
            Toast.makeText(this, "Akun diklik", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun setKegiatanHariIni(tanggal: String) {
        tvKegiatan.text = "Kegiatan pada $tanggal"

        // Dummy data untuk simulasi
        val kegiatan = listOf("Bangun pagi", "Sholat Subuh", "Sarapan", "Belajar Android", "Istirahat")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, kegiatan)
        lvKegiatan.adapter = adapter
    }
}
