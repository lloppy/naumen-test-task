package com.example.shibarichat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shibarichat.Item

class WishlistAdapter(private val dataSet: ArrayList<Item>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView
        val priceTextView: TextView
        val linkTextView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            nameTextView = itemView.findViewById(R.id.nameView)
            priceTextView = itemView.findViewById(R.id.priceView)
            linkTextView = itemView.findViewById(R.id.linkView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val wish = dataSet.get(position)
        viewHolder.nameTextView.text = wish.name
        viewHolder.priceTextView.text = wish.price
        viewHolder.linkTextView.text = wish.link
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}