package com.lab.vtc.ui.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab.vtc.app.file.CountryCodeJson
import com.lab.vtc.app.file.readJsonFromResource
import com.lab.vtc.app.navigation.RegisterComponent
import kotlinx.coroutines.launch

@Composable
fun RegisterView(component: RegisterComponent) {
    val scope = rememberCoroutineScope()
    var code by remember { mutableStateOf("") }
    var flag by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var listData by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf<List<CountryCodeJson>?>(null) }

    LaunchedEffect(Unit) {
        data = readJsonFromResource()
        if (data != null) {
            code = data!![0].dial_code
            flag = data!![0].flag
        }

    }

    Scaffold(
        modifier = Modifier.fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            VTCRegisterTopBar(modifier = Modifier.fillMaxWidth()) {

            }
        }
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            RegisterText(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 10.dp))
            VTCRegisterTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
                    .border(2.dp, Color.Blue, RoundedCornerShape(20))
                    .clip(RoundedCornerShape(20)),
                setCode = { code },
                setFlag = { flag },
                onLeadingClick = {
                    scope.launch {
                        listData = !listData
                    }
                },
                onChange = { number = it }
            )

            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
                    .weight(1f),
                visible = listData
            ) {
                if (data != null) {
                    CountryInfo(
                        modifier = Modifier.fillMaxSize()
                            .border(2.dp, Color.LightGray, RoundedCornerShape(5))
                            .clip(RoundedCornerShape(5)),
                        data = data!!,
                        onSelect = { _flag, _code ->
                            flag = _flag
                            code = _code
                        }
                    )
                }
            }
            when (listData) {
                false -> Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                else -> Unit
            }

            SaveButton {  }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun VTCRegisterTopBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier  = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = "Quit"
        )
        TextButton(
            onClick = onClick
        ) {
            Text(
                text = "Retour"
            )
        }
    }
}

@Composable
private fun RegisterText(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Votre numero de telephone",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Il est utile de fournir une bonne raison pour\n" +
                    "laquelle le numero de telephone est requis.",
            color = Color.Gray,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )

    }
}

@Composable
private fun VTCRegisterTextField(
    setCode: () -> String,
    setFlag: () -> String,
    modifier: Modifier = Modifier,
    onLeadingClick: () -> Unit,
    onChange: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    val code = remember(setCode()) { setCode() }
    val flag = remember(setFlag()) { setFlag() }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            value = it
            onChange(value)
        },
        placeholder = {
            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = "00 00 00 00"
            )
        },
        singleLine = true,
        leadingIcon = {
            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickable(onClick = onLeadingClick),
                text = " $flag  $code  ",
                fontWeight = FontWeight.Bold
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

@Composable
private fun CountryInfo(
    modifier: Modifier = Modifier,
    data: List<CountryCodeJson>,
    onSelect: (flag: String, code: String) -> Unit
) {
    Box(
        modifier = modifier
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(5)),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = data, key = { it.name }) { country ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            onSelect(country.flag, country.dial_code)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${country.flag} ${country.name}", maxLines = 1, overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Text(country.dial_code)
                }
            }
        }
    }
}

@Composable
private fun SaveButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue
        ),
        onClick = onClick
    ) {
        Text(
            text = "Continuer",
            color = Color.White
        )
    }
}