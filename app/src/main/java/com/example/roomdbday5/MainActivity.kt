package com.example.roomdbday5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomdbday5.ui.theme.RoomDbDay5Theme
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDbDay5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )  {
                    var mobnum by remember {
                        mutableStateOf("")
                    }
                    var mobnum2 by remember {
                        mutableStateOf("")
                    }
                    var mobnum3 by remember {
                        mutableStateOf("")
                    }
                    var passwrd by remember {
                        mutableStateOf("")
                    }
                    var newpasswrd by remember {
                        mutableStateOf("")
                    }
                    var getpswd by remember {
                        mutableStateOf("")
                    }

                    var getmobNum by remember {
                        mutableStateOf("")
                    }

                    var saveData by remember {
                        mutableStateOf(false)
                    }
                    var updateData by remember {
                        mutableStateOf(false)
                    }
                    var idData by remember {
                        mutableStateOf(false)
                    }
                    var delAlldata by remember {
                        mutableStateOf(false)
                    }
                    var getpwd by remember {
                        mutableStateOf(false)
                    }

                    val database = MobileDataDB.getInstance(applicationContext).mobileDao()

                    var scope = rememberCoroutineScope()
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally){
                        //enter data mob and paswd
                        item {
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                TextField(value = mobnum, onValueChange = {mobnum = it},label={ Text(text = "Mobile No.")},modifier = Modifier.fillMaxWidth())
                                TextField(value = passwrd, onValueChange = {passwrd = it},label={ Text(text = "Password")},modifier = Modifier.fillMaxWidth())
                                Button(onClick = { saveData =!saveData }) {
                                    Text(text = "Save Details")
                                }
                                if (saveData){
                                    var data = MobileEntity(0,mobnum,passwrd)

                                    scope.launch {
                                        try {
                                            database.dataInsert(data)
                                            saveData = !saveData
                                        }
                                        catch (ex: Exception) {
                                            println("Error cancelled")
                                        }
                                    }
                                }
                            }
                        }
                        //know passwd
                        item {
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                TextField(value = mobnum2, onValueChange = {mobnum2 = it},label={ Text(text = "Mobile No.")},modifier = Modifier.fillMaxWidth())
                                Button(onClick = { getpwd = !getpwd }) {
                                    Text(text = "Get Password")
                                }
                                Text(text = "Your Password is : $getpswd")
                            }
                            if(getpwd){
                                scope.launch {
                                    try {
                                        getpswd = database.searchPswd(mobnum2)
                                    }
                                    catch (ex: Exception) {
                                        println("Error cancelled")
                                    }
                                }
                            }
                        }
                        //for update
                        item {
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(modifier = Modifier.padding(8.dp),  horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    OutlinedTextField(value = mobnum3, onValueChange = {mobnum3 = it},label={ Text(text = "Mobile No.")}, modifier = Modifier.weight(1f))
                                    OutlinedTextField(value = newpasswrd, onValueChange = {newpasswrd = it},label={ Text(text = "New Password")},modifier = Modifier.weight(1f))
                                }
                                Button(onClick = { updateData = !updateData }) {
                                    Text(text = "Update password")
                                }
                                if (updateData){
                                    scope.launch {
                                    try {
                                        var id = database.searchId(mobnum = mobnum3)
                                        database.dataUpdate(MobileEntity(id, mobNum = mobnum3, pswd = newpasswrd))
                                    }
                                    catch (ex: Exception) {
                                        println("Error cancelled")
                                    }
                                    }
                                }

                            }
                        }
                        //for delete
                        item {
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Row() {
                                    OutlinedTextField(value = getmobNum, onValueChange = {getmobNum = it},label={ Text(text = "Type Mob No.")},modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp))

                                    if(idData){
                                        scope.launch {
                                            try {
                                                val idnew = database.searchId(getmobNum)
                                                database.deleteMobData(MobileEntity(idnew,getmobNum,""))
                                                idData = !idData
                                            }
                                            catch (ex: Exception) {
                                                println("Error cancelled")
                                            }
                                        }
                                    }
                                    Button(onClick = { idData=!idData },modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)) {
                                        Text(text = "Drop data")
                                    }

                                }

                                Button(onClick = { delAlldata = !delAlldata }) {
                                    Text(text = "Delete All data")
                                }

                                if (delAlldata){
                                    scope.launch {
                                        try {
                                            database.deleteAll()
                                        }
                                        catch (ex: Exception) {
                                            println("Error cancelled")
                                        }
                                    }
                                }

                            }
                        }
                    }


                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MobileRoomDB(applicationContext: Context,modifier: Modifier = Modifier)

//@Preview(showBackground = true)
//@Composable
//fun MobileRoomDBPreview() {
//    RoomDbDay5Theme {
//        MobileRoomDB(applicationContext = Context)
//    }
//}