package com.example.currencyi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.*
import com.example.currencyi.dialog.FirstDialogCallBack
import com.example.currencyi.dialog.FirstDialogFragment
import com.example.currencyi.itemtouchhelper.DragDrop
import com.example.currencyi.itemtouchhelper.ItemTouchDelegate
import com.example.currencyi.itemtouchhelper.SwipeRight
import com.example.currencyi.model.Add
import com.example.currencyi.model.Currency
import com.example.currencyi.ui.Adapter

class MainActivity : AppCompatActivity(), ItemTouchDelegate, FirstDialogCallBack {
    private var currencyList = mutableListOf(
        Currency("Тенге, Казахстан", 150000, 10, R.drawable.kz),
        Currency("Евро, ОС", 70000, 9, R.drawable.eu),
        Currency("Лира, Турция", 15000, 8, R.drawable.tur),
        Currency("Доллары, США", 120000, 7, R.drawable.usa),
        Currency("Евро, ОС", 45000, 6, R.drawable.eu),
        Currency("Доллары, США", 67000, 5, R.drawable.usa),
        Currency("Тенге, Казахстан", 250000, 4, R.drawable.kz),
        Currency("Лира, Турция", 350000, 3, R.drawable.tur),
        Add()
    )

    private lateinit var defaultToolbar: androidx.appcompat.widget.Toolbar
    private  var currencyAdapter: Adapter? = null
    private var currencyManager: LinearLayoutManager? = null
    private var dragDrop: ItemTouchHelper? = null
    lateinit var openDialog: DialogFragment
    private lateinit var deleteCurrency: Currency
    private var index = 1
    private var snapPosition = RecyclerView.NO_POSITION
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // supportActionBar?.hide()
        setContentView(R.layout.activity_main)
       // setupToolbar()
        defaultToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(defaultToolbar)
        setupCurrency()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_with_submenu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val chosenIndex = when (index) {
            1 -> R.id.sort1
            2 -> R.id.sort2
            else -> 1
        }
        menu?.findItem(chosenIndex)?.isChecked = true
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort1 -> {
                currencyAdapter?.sortA()
                index = 1
                true
            }
            R.id.sort2 -> {
                currencyAdapter?.sortB()
                index = 2
                true
            }
            R.id.no_sort -> {
                currencyAdapter?.noSort()
                true
            }
            R.id.deleteCurrency -> {
                openDialog = FirstDialogFragment()
                openDialog.show(supportFragmentManager, null)
                setupToolbar()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupCurrency() {
        currencyManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val scroll: ()-> Unit = {
            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference(): Int = SNAP_TO_START
            }
            smoothScroller.targetPosition = currencyAdapter?.itemCount ?: 0
            currencyManager?.startSmoothScroll(smoothScroller)
        }
        currencyAdapter = Adapter(this, scroll, changedToolbar())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.apply {
            adapter = currencyAdapter
            layoutManager = currencyManager
            itemAnimator = DefaultItemAnimator()
        }

        val swipeRight = ItemTouchHelper(SwipeRight(currencyAdapter))
        dragDrop = ItemTouchHelper(DragDrop())

        swipeRight.attachToRecyclerView(recyclerView)
        dragDrop?.attachToRecyclerView(recyclerView)

        currencyAdapter?.setItems(currencyList)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                snapHelper.findSnapView(currencyManager)?.let {
                    position = currencyManager?.getPosition(it)!!
                    if (snapPosition != position) {
                        snapPosition = position
                    }
                }
            }
        })
    }

    private fun changedToolbar(): (Currency) -> Unit {
        val newToolbar: (Currency) -> Unit = { currency ->
            defaultToolbar.title = "Item selected"
            defaultToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.new_background))
            defaultToolbar.menu.findItem(R.id.sort).isVisible = false
            defaultToolbar.menu.findItem(R.id.no_sort).isVisible = false
            defaultToolbar.menu.findItem(R.id.deleteCurrency).isVisible = true
            deleteCurrency = currency
        }
        return newToolbar
    }

    private fun setupToolbar() {
        defaultToolbar.title = "Convertor"
        defaultToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        defaultToolbar.menu.findItem(R.id.sort).isVisible = true
        defaultToolbar.menu.findItem(R.id.no_sort).isVisible = true
        defaultToolbar.menu.findItem(R.id.deleteCurrency).isVisible = false
    }

    override fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        dragDrop?.startDrag(viewHolder)
    }

    override fun onCurrencyRemove() {
        currencyAdapter?.deleteCurrency(deleteCurrency)
        openDialog.dismiss()
    }

}