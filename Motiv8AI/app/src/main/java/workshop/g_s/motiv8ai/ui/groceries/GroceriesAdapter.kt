package workshop.g_s.motiv8ai.ui.groceries

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import workshop.g_s.motiv8ai.data.model.Grocery
import workshop.g_s.motiv8ai.databinding.ListItemGroceriesBinding


class GroceriesAdapter() :
    androidx.recyclerview.widget.ListAdapter<Grocery, GroceryViewHolder>(GroceryComparator()) {

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val binding =
            ListItemGroceriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.initGroceryItem(currentList[position])
        setAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}
