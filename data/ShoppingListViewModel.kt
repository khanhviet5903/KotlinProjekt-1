package shopping.list.data
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ShoppingListViewModel(private val repository:`ShoppingListRepository.kt`): ViewModel()) {
    val allItems: Flow<List<ShoppingListItem>> = repository.getAllItems()
    fun addItem (name:String) {
        viewModelScope.launch {
            repository.updateItem(item.copy(isChecked = !item.isChecked))
        }
    }
    fun toggleItemState(item:ShoppingListItem) {
        viewModelScope.launch {
            repository.updateItemState(item)
        }
    }
    fun deleteItem(item: ShoppingListItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
}
