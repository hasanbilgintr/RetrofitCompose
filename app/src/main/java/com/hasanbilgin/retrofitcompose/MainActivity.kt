package com.hasanbilgin.retrofitcompose

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasanbilgin.retrofitcompose.model.CrytoModel
import com.hasanbilgin.retrofitcompose.service.CryptonAPI
import com.hasanbilgin.retrofitcompose.ui.theme.PurpleGrey40
import com.hasanbilgin.retrofitcompose.ui.theme.RetrofitComposeTheme
import com.hasanbilgin.retrofitcompose.ui.theme.hasan50
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitComposeTheme {
                MainScreen()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val BASE_URL = "https://raw.githubusercontent.com/"
    var cryptoModels = remember { mutableStateListOf<CrytoModel>() }

    val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CryptonAPI::class.java)

    val call = retrofit.getData()

    call.enqueue(object : Callback<List<CrytoModel>> {
        override fun onResponse(call: Call<List<CrytoModel>>, response: Response<List<CrytoModel>>) {
            if (response.isSuccessful) {
                response.body()?.let {
//                    it.forEach {
////                        println(it.currency + " = " + it.price)
//                    }
                    cryptoModels.addAll(it)
                }
            }
        }

        override fun onFailure(call: Call<List<CrytoModel>>, t: Throwable) {
            t.printStackTrace()
        }

    })

    Scaffold(topBar = { AppBar() }) {
        CrytoList(cryptos = cryptoModels)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {

    TopAppBar(title = { Text(text = "I'm a TopAppBar", fontSize = 26.sp) },colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Yellow ), modifier = Modifier.padding(2.dp),navigationIcon = {
        IconButton(onClick = {/* Do Something*/ }) {
            Icon(Icons.Filled.ArrowBack, null)
        }
    }, actions = {
        IconButton(onClick = {/* Do Something*/ }) {
            Icon(Icons.Filled.Share, null)
        }
        IconButton(onClick = {/* Do Something*/ }) {
            Icon(Icons.Filled.Settings, null)
        }
    })
    // Text("Hello World")


}


@Composable
fun CrytoList(cryptos: List<CrytoModel>) {
    //lazy column adnroid compose
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(cryptos) { crypto ->
            CryptoRow(cryptoRow = crypto)


        }
    }

}


@Composable
fun CryptoRow(cryptoRow: CrytoModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surface)) {
        Text(text = cryptoRow.currency, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(2.dp), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Text(text = cryptoRow.price, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(2.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitComposeTheme {
//        MainScreen()
        CryptoRow(cryptoRow = CrytoModel("BTC", "123456"))
    }
}