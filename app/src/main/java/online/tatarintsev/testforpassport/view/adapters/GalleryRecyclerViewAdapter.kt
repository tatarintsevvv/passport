package online.tatarintsev.testforpassport.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import online.tatarintsev.testforpassport.R

class GalleryRecyclerViewAdapter(
    private var items: List<String>,
    private val clickAction: (String) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var countInRow: Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GalleryRowViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.gallery_row_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(countInRow > 0) {
            val rowLayout = holder.itemView.findViewById(R.id.row_layout) as LinearLayout
            for(i in 0 until rowLayout.childCount) {
                (rowLayout.getChildAt(i) as? ImageView)?.let { imageView ->
                    val index = position * countInRow + i
                    if(index >= items.size) {
                        imageView.visibility = View.GONE
                    } else {
                        imageView.visibility = View.VISIBLE
                        val item = items[position * countInRow + i]
                        Glide.with(rowLayout.context)
                            .load(item)
                            .error(R.mipmap.ic_launcher)
                            .into(imageView)
                        imageView.setOnClickListener { clickAction.invoke(item) }
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return when(countInRow) {
            0 -> items.size
            else -> {
                var count = items.size / countInRow
                if((items.size % countInRow) > 0) {
                    count++
                }
                count
            }
        }
    }

    fun updateItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class GalleryRowViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val rowLayout: LinearLayout = view.findViewById(R.id.row_layout) as LinearLayout
        val images: List<ImageView> =
            if(rowLayout.childCount > 0) {
                val buffer: MutableList<ImageView> = mutableListOf()
                for(i in 0 until rowLayout.childCount) {
                    (rowLayout.getChildAt(i) as? ImageView)?.let { imageView ->
                        buffer.add(imageView)
                    }
                }
                countInRow = buffer.size
                buffer
            } else {
                countInRow = 0
                listOf()
            }

    }
}