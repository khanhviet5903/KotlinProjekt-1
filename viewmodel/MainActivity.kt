m.example.shopping_appkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.shoppinglist.data.ShoppingListDatabase
import com.yourcompany.shoppinglist.data.ShoppingListItem
import com.yourcompany.shoppinglist.data.ShoppingListRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ShoppingListApp()
            }
        }
    }
}

@Composable
fun ShoppingListApp() {
    val context = LocalContext.current
    val database = remember { ShoppingListDatabase.getDatabase(context) }
    val repository = remember { ShoppingListRepository(database.shoppingListDao()) }
    val viewModel: ShoppingListViewModel = viewModel { ShoppingListViewModel(repository) }

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Shopping List",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (showAddDialog) {
                AddItemDialog(
                    onDismiss = { showAddDialog = false },
                    onConfirm = { itemName ->
                        viewModel.addItem(itemName)
                        showAddDialog = false
                    }
                )
            }

            val items by viewModel.allItems.collectAsState(initial = emptyList())

            if (items.isEmpty()) {
                EmptyState()
            } else {
                ShoppingList(
                    items = items,
                    onItemClick = { viewModel.toggleItemState(it) },
                    onDeleteClick = { viewModel.deleteItem(it) }
                )
            }
        }
    }
}

@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Shopping Item") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("What do you want to buy?") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (text.isNotBlank()) {
                        onConfirm(text.trim())
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your shopping list is empty",
                fontSize = 18.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tap the + button to add items",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ShoppingList(
    items: List<ShoppingListItem>,
    onItemClick: (ShoppingListItem) -> Unit,
    onDeleteClick: (ShoppingListItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            ShoppingListItemRow(
                item = item,
                onItemClick = { onItemClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
fun ShoppingListItemRow(
    item: ShoppingListItem,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { onItemClick() }
                modifier = Modifier.padding(end = 16.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = item.name,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                color = if (item.isChecked) Color.Gray else MaterialTheme.colors.onSurface
            )

            IconButton(onClick = onDeleteClick) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Pink
                )
            }
        }
    }
}
