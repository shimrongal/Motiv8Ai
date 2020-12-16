package workshop.g_s.motiv8ai.ui.groceries

import android.graphics.Color
import android.graphics.LightingColorFilter
import androidx.recyclerview.widget.RecyclerView
import workshop.g_s.motiv8ai.data.model.Grocery
import workshop.g_s.motiv8ai.databinding.ListItemGroceriesBinding

class GroceryViewHolder(private val binding: ListItemGroceriesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun initGroceryItem(grocery: Grocery) {
        binding.grocery = grocery
        val parseColor = Color.parseColor(grocery.bagColor)
        val lightingColorFilter = LightingColorFilter(parseColor, parseColor)
        binding.ivCircle.colorFilter = lightingColorFilter
    }
}
