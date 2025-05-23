package shopping.list.data

import androidx.room.PrimaryKey

data class ShoppingListItem.kt(
    @PrimaryKey
    val id:String,
    val name: String,
    val timestamp: Long,
    val isChecked: Boolean
)


