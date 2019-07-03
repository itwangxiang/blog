package cn.todev.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearSnapHelper

class MainActivity : AppCompatActivity() {

    var mList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        for (i in 0..9) {
            mList.add(R.drawable.pic4)
            mList.add(R.drawable.pic5)
            mList.add(R.drawable.pic6)
        }

        recyclerView.layoutManager = GalleryLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = CardAdapter(mList)
        LinearSnapHelper().attachToRecyclerView(recyclerView)
    }
}

class CardAdapter(mList: ArrayList<Int>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    private var mList = ArrayList<Int>()

    init {
        this.mList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_card_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mImageView.setImageResource(mList[position])
        holder.mImageView.setOnClickListener { ToastUtils.showLong("" + position) }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView = itemView.findViewById(R.id.imageView) as ImageView
    }

}

class GalleryLayoutManager : LinearLayoutManager {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)

        val centerPos = getCenterVisibleItemPosition()
        findViewByPosition(centerPos)?.run {
            scaleX = 1.05f
            scaleY = 1.05f
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State?): Int {
        val centerPos = findFirstVisibleItemPosition()
        findViewByPosition(centerPos)?.run {
            scaleX = 1.05f
            scaleY = 1.05f
        }
        findViewByPosition(centerPos - 1)?.run {
            scaleX = 1f
            scaleY = 1f
        }
        findViewByPosition(centerPos + 1)?.run {
            scaleX = 1f
            scaleY = 1f
        }
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    private fun getCenterVisibleItemPosition(): Int {
        val firstVisiblePos = findFirstVisibleItemPosition()
        val lastVisiblePos = findLastVisibleItemPosition()

        return (firstVisiblePos + lastVisiblePos) / 2
    }

}


