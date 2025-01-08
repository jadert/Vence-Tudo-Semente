package com.jadert.vencetudo_semente

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jadert.vencetudo_semente.ui.theme.VenceTudoSementeTheme
import kotlinx.serialization.Serializable
import kotlin.math.abs
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tutorialImg = listOf(
            R.drawable.inicial,
            R.drawable.arredonda,
            R.drawable.tipo,
            R.drawable.resultado,
            R.drawable.eng
        )
        val tutorialTxt = listOf(
            "O primeiro passo é digitar a quantidade de sementes por metro desejadas e a quantidade de furos no dico utilizado, feito isso clique em calcular.",
            "Se o valor exato de sementes não for encontrado, será buscado o mais próximo acima ou abaixo do valor desejado. Você pode selecionar qual deseja",
            "Também existe a opção de regulagem mais fácil onde você vai mudar apenas os eixos A e B, ou pode buscar a configuração mais próxima o possivel do valor deseado",
            "Aqui é mostrada qual engrenagem deve ser colocada em cada eixo bem como a quantida real de sementes, você pode alterar as opções de ajuste livremente.",
            "Esse é um diagramamdas dos eixos na plantadeira e com seu respectivo nome, sendo eles: Eixo A, Eixo B, Eixo C e Eixo D."
        )

        fun ehInteiro(num: String): Boolean {
            return num.toIntOrNull() != null
        }

        enableEdgeToEdge()
        setContent {
            VenceTudoSementeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = TelaInicial
                ) {
                    composable<TelaInicial>{
                        var sementesMetro by remember { mutableStateOf("") }
                        var furosDisco by remember { mutableStateOf("") }

                        val scope = rememberCoroutineScope()
                        val snackbarHostState = remember { SnackbarHostState() }

                        Row(
                            modifier = Modifier
                                .padding(48.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            SnackbarHost(hostState = snackbarHostState)
                            FloatingActionButton(onClick = {
                                navController.navigate(TelaSobre)
                            }) {
                                Icon(Icons.Filled.Info, "Informações.")
                            }
                        }
                        Column (
                            modifier = Modifier
                                .padding(48.dp, 200.dp, 48.dp, 48.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ){
                            OutlinedTextField(
                                value = sementesMetro,
                                onValueChange = { sementesMetro = it },
                                label = { Text("Sementes por metro") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(
                                value = furosDisco,
                                onValueChange = { furosDisco = it },
                                label = { Text("Furos no Disco") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Button(onClick = {
                                if (sementesMetro.isEmpty()) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Informe o número de sementes")
                                    }
                                } else if (furosDisco.isEmpty()) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Informe o número de furos")
                                    }
                                } else {
                                    if (!ehInteiro(sementesMetro)){
                                        scope.launch {
                                            snackbarHostState.showSnackbar("O valor de sementes deve ser um número inteiro")
                                        }
                                    } else if (!ehInteiro(furosDisco)){
                                        scope.launch {
                                            snackbarHostState.showSnackbar("O valor de furos deve ser um número inteiro")
                                        }
                                    } else {
                                        if ((sementesMetro.toInt() <= 0) or (sementesMetro.toInt() > 100)) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar("O número de sementes deve ser entre 1 e 100")
                                            }
                                        } else if (furosDisco.toInt() <= 0) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar("O número de furos deve ser maior que 0")
                                            }
                                        } else {
                                            navController.navigate(TelaResultado(
                                                pSemente = sementesMetro.toInt(),
                                                pFuro = furosDisco.toInt()
                                            ))
                                        }
                                    }
                                }
                            }) {
                                Text(text = "Calcular", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    composable<TelaResultado> {
                        val args = it.toRoute<TelaResultado>()
                        val df = java.text.DecimalFormat("#.##")
                        val valorVT = 0.1520833333333396
                        val relacao1 = arrayOf( arrayOf(14, 15, 0.93333333333333), arrayOf(14, 17, 0.82352941176471), arrayOf(14, 19, 0.73684210526316), arrayOf(14, 21, 0.66666666666667),
                            arrayOf(14, 23, 0.60869565217391), arrayOf(16, 15, 1.0666666666667), arrayOf(16, 17, 0.94117647058824), arrayOf(16, 19, 0.84210526315789), arrayOf(16, 21, 0.76190476190476),
                            arrayOf(16, 23, 0.69565217391304), arrayOf(18, 15, 1.2), arrayOf(18, 17, 1.0588235294118), arrayOf(18, 19, 0.94736842105263), arrayOf(18, 21, 0.85714285714286),
                            arrayOf(18, 23, 0.78260869565217), arrayOf(20, 15, 1.3333333333333), arrayOf(20, 17, 1.1764705882353), arrayOf(20, 19, 1.0526315789474), arrayOf(20, 21, 0.95238095238095),
                            arrayOf(20, 23, 0.8695652173913), arrayOf(24, 15, 1.6), arrayOf(24, 17, 1.4117647058824), arrayOf(24, 19, 1.2631578947368), arrayOf(24, 21, 1.1428571428571),
                            arrayOf(24, 23, 1.0434782608696) )
                        val relacao2 =  arrayOf( arrayOf(24, 21, 1.1428571428571), arrayOf(24, 14, 1.7142857142857), arrayOf(14, 21, 0.66666666666667), arrayOf(14, 14, 1) )

                        var tipoEixo by remember { mutableStateOf(false) }
                        var ajustePop by remember { mutableStateOf(false) }
                        val eixoA = Array(4) { 0 }
                        val eixoB = Array(4) { 0 }
                        val eixoC = arrayOf(0, 0, 14, 14)
                        val eixoD = arrayOf(0, 0, 14, 14)
                        val sementeReal = Array(4) { Double.MAX_VALUE }

                        for (dados in relacao1) {
                            val tmp = args.pFuro * (dados[2].toDouble() * valorVT)
                            val diferenca = tmp - args.pSemente

                            if (diferenca > 0) {
                                if (diferenca < (sementeReal[2]-args.pSemente)) {
                                    eixoA[2] = dados[0].toInt()
                                    eixoB[2] = dados[1].toInt()
                                    sementeReal[2] = tmp
                                }
                            } else {
                                if (abs(diferenca) <= abs(sementeReal[3]-args.pSemente)) {
                                    eixoA[3] = dados[0].toInt()
                                    eixoB[3] = dados[1].toInt()
                                    sementeReal[3] = tmp
                                }
                            }
                            for (dados2 in relacao2) {
                                val tmp2 = args.pFuro * ((dados[2].toDouble() * dados2[2].toDouble()) * valorVT)
                                val diferenca2 = tmp2 - args.pSemente
                                if (diferenca2 > 0) {
                                    if (diferenca2 < (sementeReal[0]-args.pSemente)) {
                                        eixoA[0] = dados[0].toInt()
                                        eixoB[0] = dados[1].toInt()
                                        eixoC[0] = dados2[0].toInt()
                                        eixoD[0] = dados2[1].toInt()
                                        sementeReal[0] = tmp2
                                    }
                                } else {
                                    if (abs(diferenca2) <= abs(sementeReal[1]-args.pSemente)) {
                                        eixoA[1] = dados[0].toInt()
                                        eixoB[1] = dados[1].toInt()
                                        eixoC[1] = dados2[0].toInt()
                                        eixoD[1] = dados2[1].toInt()
                                        sementeReal[1] = tmp2
                                    }
                                }
                            }
                        }

                        val opcaoSemente = if (tipoEixo) {
                            if (ajustePop) 0 else 1
                        } else {
                            if (ajustePop) 2 else 3
                        }
                        Row(
                            modifier = Modifier
                                .padding(48.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            FloatingActionButton(onClick = {
                                navController.navigate(TelaInicial)
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar.")
                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(10.dp, 120.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .width(100.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = if (tipoEixo) "+Próximo" else "+Fácil",
                                    fontWeight = FontWeight.Medium)
                                androidx.compose.material3.Switch(
                                    checked = tipoEixo,
                                    onCheckedChange = {
                                        tipoEixo = it
                                    }
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                Text("Ajustar população", fontWeight = FontWeight.Medium)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = if (ajustePop) "a cima" else "a baixo")
                                    androidx.compose.material3.Switch(
                                        checked = ajustePop,
                                        onCheckedChange = {
                                            ajustePop = it
                                        }
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Eixo A: "+eixoA[opcaoSemente], fontSize = 40.sp)
                            Text(text = "Eixo B: "+eixoB[opcaoSemente], fontSize = 40.sp)
                            Text(text = "Eixo C: "+eixoC[opcaoSemente], fontSize = 40.sp)
                            Text(text = "Eixo D: "+eixoD[opcaoSemente], fontSize = 40.sp)
                            HorizontalDivider(thickness = 2.dp)
                            Text(text = "Sementes: "+df.format(sementeReal[opcaoSemente]).toDouble(),
                                fontSize = 40.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                    composable<TelaSobre> {
                        val pagerState = rememberPagerState(
                            initialPage = 0,
                            initialPageOffsetFraction = 0f,
                            pageCount = { tutorialImg.size }
                        )
                        val scope = rememberCoroutineScope()
                        Box(modifier = Modifier.fillMaxSize()
                            .padding(0.dp,104.dp,0.dp,100.dp)) {
                            HorizontalPager(
                                beyondViewportPageCount = tutorialImg.size,
                                state = pagerState,
                                key = { tutorialImg[it] },
                                pageSize = PageSize.Fill
                            ) { index ->
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(tutorialTxt[index])
                                    Image(
                                        painter = painterResource(id = tutorialImg[index]),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(0.dp,0.dp,0.dp,50.dp)
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .clip(RoundedCornerShape(100))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .align(Alignment.BottomCenter)
                            ) {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                pagerState.currentPage - 1
                                            )
                                        }
                                    },
                                    modifier = Modifier.align(Alignment.CenterStart)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        contentDescription = "Anterior"
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                pagerState.currentPage + 1
                                            )
                                        }
                                    },
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "Proximo"
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(48.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            FloatingActionButton(onClick = {
                                navController.navigate(TelaInicial)
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar.")
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Todas as marcas pertencem a Vence Tudo - Ind. de Máquinas e Implementos Agrícolas",
                                fontWeight = FontWeight.Light)
                            Text(text = "Desenvolvido por Jader Teixeira",
                                fontWeight = FontWeight.Light)
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object TelaInicial

@Serializable
object TelaSobre

@Serializable
data class TelaResultado(
    val pSemente: Int,
    val pFuro: Int
)