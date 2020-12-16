package workshop.g_s.motiv8ai.ui.groceries

import androidx.recyclerview.widget.DiffUtil
import workshop.g_s.motiv8ai.data.model.Grocery

class GroceryComparator : DiffUtil.ItemCallback<Grocery>() {
    override fun areItemsTheSame(oldItem: Grocery, newItem: Grocery): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Grocery, newItem: Grocery): Boolean {
        return oldItem == newItem
    }
}
