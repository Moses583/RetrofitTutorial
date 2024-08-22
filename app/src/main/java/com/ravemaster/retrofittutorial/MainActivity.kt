package com.ravemaster.retrofittutorial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ravemaster.retrofittutorial.data.ProductsImplementation
import com.ravemaster.retrofittutorial.data.RetrofitInstance
import com.ravemaster.retrofittutorial.data.models.Product
import com.ravemaster.retrofittutorial.presentation.ProductsViewModel
import com.ravemaster.retrofittutorial.ui.theme.RetrofitTutorialTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ProductsViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductsViewModel(ProductsImplementation(RetrofitInstance.getProducts))
                    as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RetrofitTutorialTheme {
                val response = viewModel.response.collectAsState().value
                val context = LocalContext.current
                LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
                    viewModel.showErrorToastChannel.collectLatest {
                            show ->
                        if (show) {
                            Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
                        }
                    }
                }f
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding->
                    if (response.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center){
                            CircularProgressIndicator()
                        }
                    }else{
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(response.size){ index ->
                                Greeting(response[index])
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }



            }
        }
    }
}

@Composable
fun Greeting(response: Product, modifier: Modifier = Modifier) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(response.thumbnail)
            .size(Size.ORIGINAL)
            .build()).state

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .height(300.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        if(imageState is AsyncImagePainter.State.Error){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        if(imageState is AsyncImagePainter.State.Success){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                painter = imageState.painter,
                contentDescription = response.title)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${response.title} -- Price: ${response.price}",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = response.description,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 12.sp,)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitTutorialTheme {

    }
}