package shopping.list.data

import androidx.room.PrimaryKey

data class ShoppingListItem.kt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val id:String,
    val name: String,
    val timestamp: LocalDateTime.parse(timestamp) 
    val isChecked: Boolean = false 
)


