package com.daco.kt_kotlin.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.daco.kt_kotlin.model.Product

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val todoItems = homeViewModel.products
    var selectedItem by remember { mutableStateOf<Product?>(null) }
    var editedItem by remember { mutableStateOf<Product?>(null) }
    var itemToDelete by remember { mutableStateOf<Product?>(null) }
    var newItemName by remember { mutableStateOf("") }
    var newItemPrice by remember { mutableStateOf("") }
    var newItemDescription by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { contentPadding ->
        LazyColumn(contentPadding = contentPadding) {
            items(todoItems) { item ->
                ToDoItem(item = item, onClick = { selectedItem = it }, onLongClick = { itemToDelete = it })
            }
        }

        if (selectedItem != null) {

            var newName by remember { mutableStateOf(selectedItem?.name ?: "") }
            var newPrice by remember { mutableStateOf(selectedItem?.price.toString()) }
            var newDescription by remember { mutableStateOf(selectedItem?.description ?: "") }
            AlertDialog(
                onDismissRequest = { selectedItem = null },
                title = { Text(text = "Item Details") },
                text = {
                    Column {
                        Text(text = "ID: ${selectedItem?.id}")
                        TextField(value = newName, onValueChange = { newName = it }, label = { Text("Name") })
                        TextField(value = newPrice, onValueChange = { newPrice = it }, label = { Text("Price") })
                        TextField(value = newDescription, onValueChange = { newDescription = it }, label = { Text("Description") })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        editedItem = selectedItem?.copy(name = newName, price = newPrice.toDouble(), description = newDescription)
                        selectedItem = null
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = { selectedItem = null }) {
                        Text("Close")
                    }
                }
            )
        }
        if (itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { itemToDelete = null },
                title = { Text(text = "Confirm Deletion") },
                text = { Text(text = "Do you want to delete this item?") },
                confirmButton = {
                    Button(onClick = {
                        todoItems.remove(itemToDelete)
                        itemToDelete = null
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(onClick = { itemToDelete = null }) {
                        Text("No")
                    }
                }
            )
        }
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text(text = "Add New Item") },
                text = {
                    Column {
                        TextField(value = newItemName, onValueChange = { newItemName = it }, label = { Text("Name") })
                        TextField(value = newItemPrice, onValueChange = { newItemPrice = it }, label = { Text("Price") })
                        TextField(value = newItemDescription, onValueChange = { newItemDescription = it }, label = { Text("Description") })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val newProduct = Product(
                            id = todoItems.size + 1,
                            name = newItemName,
                            price = newItemPrice.toDouble(),
                            description = newItemDescription
                        )
                        homeViewModel.addProduct(newProduct)
                        newItemName = ""
                        newItemPrice = ""
                        newItemDescription = ""
                        showAddDialog = false
                    }) {
                        Text("Submit")
                    }
                },
                dismissButton = {
                    Button(onClick = { showAddDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
    if (editedItem != null) {
        homeViewModel.updateProduct(editedItem!!)
        editedItem = null
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoItem(item: Product, onClick: (Product) -> Unit, onLongClick: (Product) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(item) }
            .background(color = Color.LightGray, shape = MaterialTheme.shapes.medium)
            .combinedClickable(onClick = { onClick(item) }, onLongClick = { onLongClick(item) })
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp),

            ) {
            Text(text = item.id.toString())
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = item.name)
        }
    }
}