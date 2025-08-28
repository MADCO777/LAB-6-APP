
package com.example.contador

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.FlexboxLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tvCounter: TextView
    private lateinit var tvTotalIncrements: TextView
    private lateinit var tvTotalDecrements: TextView
    private lateinit var tvMaxValue: TextView
    private lateinit var tvMinValue: TextView
    private lateinit var tvTotalChanges: TextView
    private lateinit var btnIncrement: Button
    private lateinit var btnDecrement: Button
    private lateinit var btnReset: Button
    private lateinit var flexboxHistory: FlexboxLayout

    private var currentValue = 0
    private var totalIncrements = 0
    private var totalDecrements = 0
    private var maxValue = 0
    private var minValue = 0
    private var totalChanges = 0
    private val history = mutableListOf<HistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
        updateUI()
    }

    private fun initViews() {
        tvCounter = findViewById(R.id.tvCounter)
        tvTotalIncrements = findViewById(R.id.tvTotalIncrements)
        tvTotalDecrements = findViewById(R.id.tvTotalDecrements)
        tvMaxValue = findViewById(R.id.tvMaxValue)
        tvMinValue = findViewById(R.id.tvMinValue)
        tvTotalChanges = findViewById(R.id.tvTotalChanges)
        btnIncrement = findViewById(R.id.btnIncrement)
        btnDecrement = findViewById(R.id.btnDecrement)
        btnReset = findViewById(R.id.btnReset)
        flexboxHistory = findViewById(R.id.flexboxHistory)
    }

    private fun setupListeners() {
        btnIncrement.setOnClickListener {
            currentValue++
            totalIncrements++
            totalChanges++
            updateMaxValue()
            addToHistory(currentValue, true)
            updateUI()
        }

        btnDecrement.setOnClickListener {
            currentValue--
            totalDecrements++
            totalChanges++
            updateMinValue()
            addToHistory(currentValue, false)
            updateUI()
        }

        btnReset.setOnClickListener {
            currentValue = 0
            totalIncrements = 0
            totalDecrements = 0
            maxValue = 0
            minValue = 0
            totalChanges = 0
            history.clear()
            flexboxHistory.removeAllViews()
            updateUI()
        }
    }

    private fun updateMaxValue() {
        if (totalChanges == 1 || currentValue > maxValue) {
            maxValue = currentValue
        }
    }

    private fun updateMinValue() {
        if (totalChanges == 1 || currentValue < minValue) {
            minValue = currentValue
        }
    }

    private fun addToHistory(value: Int, isIncrement: Boolean) {
        history.add(HistoryItem(value, isIncrement))

        // Crear botón para el historial
        val historyButton = Button(this).apply {
            text = value.toString()
            setTextColor(Color.WHITE)
            textSize = 14f

            // Establecer color de fondo

            minWidth = 80
            minHeight = 80
        }

        // Configurar parámetros del layout
        val params = FlexboxLayout.LayoutParams(
            FlexboxLayout.LayoutParams.WRAP_CONTENT,
            FlexboxLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 8, 8, 8)
        }

        historyButton.layoutParams = params
        flexboxHistory.addView(historyButton)
    }

    private fun updateUI() {
        tvCounter.text = currentValue.toString()
        tvTotalIncrements.text = totalIncrements.toString()
        tvTotalDecrements.text = totalDecrements.toString()
        tvMaxValue.text = maxValue.toString()
        tvMinValue.text = minValue.toString()
        tvTotalChanges.text = totalChanges.toString()
    }

    data class HistoryItem(
        val value: Int,
        val isIncrement: Boolean
    )
}