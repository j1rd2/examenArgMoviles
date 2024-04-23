package com.example.examenarg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examenarg.ui.theme.ExamenArgTheme
import com.example.examenarg.data.model.DNSRecord
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState
import com.example.examenarg.data.repository.DNSRepository
import com.example.examenarg.views.viewmodel.DNSViewModel
import com.example.examenarg.views.viewmodel.DNSViewModelFactory

class MainActivity : ComponentActivity() {

    private val dnsRepository = DNSRepository() // Create an instance of DNSRepository

    private val viewModel: DNSViewModel by viewModels {
        DNSViewModelFactory(dnsRepository) // Pass it to the ViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenArgTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: DNSViewModel = viewModel()) {
    var text by remember { mutableStateOf("") }
    val dnsRecords by viewModel.dnsRecords.observeAsState(initial = emptyList())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter Domain") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    viewModel.fetchDNSRecords(text)
                }),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { viewModel.fetchDNSRecords(text) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Search")
            }
            DNSRecordsList(dnsRecords)
        }
    }
}


@Composable
fun DNSRecordsList(dnsRecords: List<DNSRecord>) {
    LazyColumn {
        items(dnsRecords) { record ->
            Text("Type: ${record.type}, Host: ${record.host}, Result: ${record.result}",
                modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExamenArgTheme {
        MainScreen()
    }
}
