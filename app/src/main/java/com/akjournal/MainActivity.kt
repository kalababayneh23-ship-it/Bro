package com.akjournal

import android.app.*
import android.os.Bundle
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.*
import android.widget.*

class MainActivity : Activity() {

    private val bg = Color.rgb(8, 10, 15)
    private val card = Color.rgb(17, 20, 28)
    private val purple = Color.rgb(139, 92, 246)
    private val green = Color.rgb(34, 197, 94)
    private val red = Color.rgb(239, 68, 68)

    private lateinit var content: LinearLayout

    private fun text(
        value: String,
        size: Float = 14f,
        bold: Boolean = false,
        color: Int = Color.WHITE
    ) = TextView(this).apply {
        text = value
        textSize = size
        setTextColor(color)
        setPadding(0, 5, 0, 5)
        if (bold) setTypeface(null, 1)
    }

    private fun card(): LinearLayout =
        LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 16, 18, 16)
            background = GradientDrawable().apply {
                setColor(this@MainActivity.card)
                cornerRadius = 22f
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboard()
    }

    private fun dashboard() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(20, 25, 20, 12)
        }

        val title = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        title.addView(text("A.K JOURNAL", 25f, true))
        title.addView(
            text(
                "MAVEN-INSPIRED TRADING DASHBOARD",
                11f,
                false,
                Color.LTGRAY
            )
        )

        header.addView(
            title,
            LinearLayout.LayoutParams(0, -2, 1f)
        )

        val addTrade = Button(this).apply {
            text = "+ Add Trade"
            setOnClickListener {
                addTradeDialog()
            }
        }

        header.addView(addTrade)
        root.addView(header)

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 5, 16, 100)
        }

        val scroll = ScrollView(this)
        scroll.addView(content)

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                -1,
                0,
                1f
            )
        )

        val navigation = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(4, 8, 4, 8)
            setBackgroundColor(Color.rgb(13, 15, 22))
        }

        val pages = listOf(
            "⌂\nDashboard",
            "▣\nTrades",
            "▦\nCalendar",
            "◈\nAnalytics",
            "✎\nJournal"
        )

        pages.forEachIndexed { index, label ->

            val item = text(
                label,
                11f,
                true,
                Color.LTGRAY
            )

            item.gravity = Gravity.CENTER

            item.setOnClickListener {
                when (index) {
                    0 -> dashboard()
                    1 -> trades()
                    2 -> calendar()
                    3 -> analytics()
                    4 -> journal()
                }
            }

            navigation.addView(
                item,
                LinearLayout.LayoutParams(
                    0,
                    65,
                    1f
                )
            )
        }

        root.addView(navigation)

        setContentView(root)
    }

    private fun stat(
        title: String,
        value: String,
        change: String,
        positive: Boolean = true
    ): LinearLayout {

        val box = card()

        box.addView(
            text(
                title,
                12f,
                false,
                Color.LTGRAY
            )
        )

        box.addView(
            text(
                value,
                22f,
                true
            )
        )

        box.addView(
            text(
                change,
                12f,
                true,
                if (positive) green else red
            )
        )

        return box
    }

    private fun dashboard() {

        if (!::content.isInitialized) {
            createBase()
        }

        content.removeAllViews()

        content.addView(
            text(
                "Account Overview",
                19f,
                true
            )
        )

        val grid = GridLayout(this)
        grid.columnCount = 2

        val stats = listOf(
            Triple("Balance", "$2,147.50", "+7.38%"),
            Triple("Today's P&L", "+$147.50", "+6.9%"),
            Triple("Win Rate", "68.4%", "+4.2%"),
            Triple("Profit Factor", "2.31", "+0.18"),
            Triple("Average R", "1.42R", "+0.11R"),
            Triple("Max Drawdown", "-3.80%", "Within limit")
        )

        stats.forEach {

            val box = stat(
                it.first,
                it.second,
                it.third,
                !it.second.startsWith("-")
            )

            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = 145
                columnSpec =
                    GridLayout.spec(
                        GridLayout.UNDEFINED,
                        1f
                    )

                setMargins(5, 5, 5, 5)
            }

            grid.addView(box, params)
        }

        content.addView(grid)

        val equity = card()

        equity.addView(
            text(
                "Equity Curve",
                18f,
                true
            )
        )

        equity.addView(
            text(
                "Last 30 days",
                12f,
                false,
                Color.LTGRAY
            )
        )

        equity.addView(
            text(
                "▁▂▁▃▄▃▅▄▆▇▆▇█▇▉▇█▉",
                32f,
                true,
                purple
            )
        )

        equity.addView(
            text(
                "$2,000  →  $2,147.50",
                13f,
                false,
                Color.LTGRAY
            )
        )

        content.addView(
            equity,
            LinearLayout.LayoutParams(
                -1,
                175
            ).apply {
                setMargins(0, 12, 0, 10)
            }
        )

        val performance = card()

        performance.addView(
            text(
                "Performance",
                18f,
                true
            )
        )

        val performanceRows = listOf(
            "Best Day                 +$182.40",
            "Average Win              +$74.20",
            "Average Loss             -$38.60",
            "Expectancy               +0.62R",
            "Total Trades             38"
        )

        performanceRows.forEach {
            performance.addView(
                text(it, 14f)
            )
        }

        content.addView(performance)

        val recent = card()

        recent.addView(
            text(
                "Recent Trades",
                18f,
                true
            )
        )

        val trades = listOf(
            "XAUUSD   BUY    +$72.40    +2.1R",
            "NAS100   SELL   +$51.20    +1.6R",
            "XAUUSD   SELL   -$18.30    -0.6R",
            "BTCUSD   BUY    +$42.10    +1.3R"
        )

        trades.forEach {
            recent.addView(
                text(it, 14f)
            )
        }

        content.addView(recent)
    }

    private fun createBase() {
        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setBackgroundColor(bg)
        content = root
    }

    private fun trades() {
        content.removeAllViews()

        content.addView(
            text("Trade History", 22f, true)
        )

        val list = card()

        val trades = listOf(
            "OCT 08   XAUUSD   BUY    +2.10R   +$72.40",
            "OCT 08   NAS100   SELL   +1.60R   +$51.20",
            "OCT 07   XAUUSD   SELL   -0.60R   -$18.30",
            "OCT 06   BTCUSD   BUY    +1.30R   +$42.10",
            "OCT 06   XAUUSD   BUY    +2.70R   +$96.50",
            "OCT 05   NAS100   SELL   -0.90R   -$31.40"
        )

        trades.forEach {
            list.addView(
                text(it, 13f)
            )
        }

        content.addView(list)
    }

    private fun calendar() {
        content.removeAllViews()

        content.addView(
            text(
                "October 2026",
                22f,
                true
            )
        )

        val cal = card()

        cal.addView(
            text(
                "MON   TUE   WED   THU   FRI   SAT   SUN",
                12f,
                false,
                Color.LTGRAY
            )
        )

        cal.addView(
            text(
                """
                1     2     3     4
                5     6     7     8     9    10    11
                12   13    14    15    16    17    18
                19   20    21    22    23    24    25
                26   27    28    29    30    31
                """.trimIndent(),
                17f,
                true
            )
        )

        content.addView(cal)
    }

    private fun analytics() {
        content.removeAllViews()

        content.addView(
            text(
                "Analytics",
                22f,
                true
            )
        )

        val sections = listOf(
            "Risk / Reward" to
                    "Average winner +1.86R | Average loser -0.74R",

            "Sessions" to
                    "New York 58% | London 31% | Asia 11%",

            "Symbols" to
                    "XAUUSD 61% | NAS100 26% | BTCUSD 13%",

            "Setups" to
                    "Liquidity Sweep 72% | Breakout 18% | Other 10%",

            "Discipline" to
                    "Rule adherence 91% | Revenge trades 2"
        )

        sections.forEach {

            val box = card()

            box.addView(
                text(
                    it.first,
                    17f,
                    true
                )
            )

            box.addView(
                text(
                    it.second,
                    13f,
                    false,
                    Color.LTGRAY
                )
            )

            content.addView(box)

            content.addView(
                Space(this).apply {
                    minimumHeight = 8
                }
            )
        }
    }

    private fun journal() {

        content.removeAllViews()

        content.addView(
            text(
                "Trading Journal",
                22f,
                true
            )
        )

        val journal = card()

        journal.addView(
            text(
                "Today's Reflection",
                18f,
                true
            )
        )

        journal.addView(
            text(
                "What went well?",
                14f,
                true
            )
        )

        journal.addView(
            text(
                "Waited for confirmation and respected risk.",
                13f,
                false,
                Color.LTGRAY
            )
        )

        journal.addView(
            text(
                "What to improve?",
                14f,
                true
            )
        )

        journal.addView(
            text(
                "Avoid entering before the liquidity sweep.",
                13f,
                false,
                Color.LTGRAY
            )
        )

        content.addView(journal)
    }

    private fun addTradeDialog() {

        val dialog = Dialog(this)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 25, 28, 20)
            setBackgroundColor(card)
        }

        layout.addView(
            text(
                "Add Trade",
                23f,
                true
            )
        )

        val symbol = EditText(this)
        symbol.hint = "Symbol (XAUUSD)"
        layout.addView(symbol)

        val pnl = EditText(this)
        pnl.hint = "P&L ($)"
        layout.addView(pnl)

        val rr = EditText(this)
        rr.hint = "R Multiple"
        layout.addView(rr)

        val notes = EditText(this)
        notes.hint = "Trade Notes"
        layout.addView(notes)

        val save = Button(this).apply {
            text = "SAVE TRADE"

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Trade saved to A.K Journal",
                    Toast.LENGTH_SHORT
                ).show()

                dialog.dismiss()
            }
        }

        layout.addView(save)

        dialog.setContentView(layout)
        dialog.show()
    }
}
