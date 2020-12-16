package workshop.g_s.motiv8ai.ui.groceries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_groceries.*
import workshop.g_s.motiv8ai.data.model.Grocery
import workshop.g_s.motiv8ai.databinding.FragmentGroceriesBinding

/**
 * Created by Gal Shimron on 16/12/2020.
 */
class GroceriesFragment : Fragment() {

    private val viewModel: GroceriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val currentView = FragmentGroceriesBinding.inflate(inflater, container, false)
        currentView.viewModel = viewModel
        return currentView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(rcvGroceries) {
            adapter = GroceriesAdapter()
        }

        viewModel.currentGrocery.observe(viewLifecycleOwner, Observer {
            it?.let {
                val tempList = mutableListOf<Grocery>()
                val groceriesAdapter = rcvGroceries.adapter as GroceriesAdapter
                if (groceriesAdapter.currentList.isNotEmpty())
                    tempList.addAll(groceriesAdapter.currentList)
                tempList.add(it)
                groceriesAdapter.submitList(tempList)
            }
        })

        viewModel.isSocketAlreadyOpen.observe(viewLifecycleOwner, {
            if (it) {
                Snackbar.make(requireView(), "The Socket is already open", 2000).show()
            }
        })
    }
}