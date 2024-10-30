package edu.ucne.proyectofinalaplicada2.presentation.algo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import edu.ucne.proyectofinalaplicada2.ui.theme.ProyectoFinalAplicada2Theme

@Composable
fun AlgoScreen(
    algoId: Int,
    viewModel: AlgoViewModel = hiltViewModel(),
    goAlgoList: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AlgoBodyScreen(
        algoId = algoId,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goAlgoList = goAlgoList
    )
}

@Composable
private fun AlgoBodyScreen(
    algoId: Int,
    uiState: AlgoUiState,
    onEvent: (AlgoUiEvent) -> Unit,
    goAlgoList: () -> Unit
){
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true, key2 = uiState.isSuccess) {
        onEvent(AlgoUiEvent.SelectedAlgo(algoId))

        if(uiState.isSuccess)
            goAlgoList()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Lista de Algos",
                onClickMenu = {}
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp)
        ){
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            label = {
                                Text("Nombre")
                            },
                            value = uiState.nombre,
                            onValueChange = {
                                onEvent(AlgoUiEvent.NombreChanged(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .focusRequester(focusRequester)
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(
                                        FocusDirection.Next
                                    )
                                }
                            )
                        )
                        uiState.errorNombre?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            label = {
                                Text("Tipo")
                            },
                            value = uiState.tipo,
                            onValueChange = {
                                onEvent(AlgoUiEvent.TipoChanged(it))
                            },
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    onEvent(AlgoUiEvent.Save)
                                }
                            )
                        )
                        uiState.errorTipo?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        OutlinedButton(
                            onClick = {
                                focusRequester.requestFocus()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "Iniciar formulario"
                            )
                            Text("Iniciar")
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        OutlinedButton(
                            onClick = {
                                onEvent(AlgoUiEvent.Save)
                            }
                        ) {
                            Icon(
                                imageVector = if(algoId == 0) Icons.Default.Add else Icons.Default.Done,
                                contentDescription = if(algoId == 0) "Crear Algo" else "Modificar Algo"
                            )
                            Text(
                                text = if(algoId == 0) "Crear" else "Modificar"
                            )
                        }
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AlgoScreenPreview(){
    ProyectoFinalAplicada2Theme {
        AlgoScreen(
            algoId = 0,
            goAlgoList = {}
        )
    }
}