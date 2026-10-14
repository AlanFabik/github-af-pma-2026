package com.example.myapp004objednavka

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp004objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 1. Binding – deklarace objektu s odloženou inicializací
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // 2. Binding – nafouknutí layoutu do binding instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 3. Binding – nastavení kořenového pohledu do okna aktivity
        setContentView(binding.root)

        // Ošetření systémových lišt
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        ViewCompat.requestApplyInsets(binding.root)

        // 4. Binding – přístup k prvkům layoutu přes binding.<id>
        binding.btnOrder.setOnClickListener {

            // Zjištění vybraného modelu kola
            val bike = when (binding.rgBikes.checkedRadioButtonId) {
                binding.rbBike1.id -> binding.rbBike1
                binding.rbBike2.id -> binding.rbBike2
                binding.rbBike3.id -> binding.rbBike3
                else -> binding.rbBike1
            }

            val fork = binding.cbFork.isChecked
            val saddle = binding.cbSaddle.isChecked
            val handleBar = binding.cbHandleBar.isChecked

            // Sestavení přehledu volitelných úprav
            val upgrades =
                (if (fork) "• Vylepšená odpružená vidlice\n" else "") +
                        (if (saddle) "• Komfortnější sedlo\n" else "") +
                        (if (handleBar) "• Vylepšená řídítka\n" else "")

            val orderText =
                "Vybraný model\n${bike.text}\n\nVolitelné úpravy\n" +
                        if (upgrades.isEmpty()) {
                            "Standardní výbava bez dalších úprav."
                        } else {
                            upgrades.trimEnd()
                        }

            binding.tvOrder.text = orderText

            Toast.makeText(
                this,
                "Objednávka kola ${bike.text} byla vytvořena.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Změna obrázku ihned při výběru modelu
        binding.rgBikes.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbBike1.id -> {
                    binding.ivBike.setImageResource(R.drawable.oiz_m10_tr)
                    binding.ivBike.contentDescription = "Ukázka kola Orbea Oiz M10"
                }

                binding.rbBike2.id -> {
                    binding.ivBike.setImageResource(R.drawable.oiz_m20_tr)
                    binding.ivBike.contentDescription = "Ukázka kola Orbea Oiz M20"
                }

                binding.rbBike3.id -> {
                    binding.ivBike.setImageResource(R.drawable.oiz_m30_tr)
                    binding.ivBike.contentDescription = "Ukázka kola Orbea Oiz M30"
                }
            }
        }
    }
}