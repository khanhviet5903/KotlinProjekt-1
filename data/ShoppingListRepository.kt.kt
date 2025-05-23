package shopping.list.data

data class `ShoppingListRepository.kt`(private val dao: ShoppingListDao) {
    fun getAllItems(): Flow<List<ShoppingListItem>> = dao.getAllItems()
    suspend fun addItem(name: String) {
        val item = ShoppingListItem(
            id= UUID.randomUUID().toString(),
            name = name,
            timestamp = System.currentTimeMillis(),
            isChecked = false
        )
        dao. addItem(item)
    }
    suspend fun updateItemState(item:ShoppingListItem) {
        dao.updateItem(item.copy(isChecked = !item.isChecked))
    }

    suspend fun deleteItem(item: ShoppingListItem) {
        dao.deleteItem(item)
    }
}
