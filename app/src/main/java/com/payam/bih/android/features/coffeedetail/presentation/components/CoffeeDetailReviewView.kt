package com.payam.bih.android.features.coffeedetail.presentation.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.payam.bih.android.R
import java.util.*

@Composable
fun CoffeeDetailReviewView(
    onReviewSubmit: (String, String, String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 4.dp),
            text = "Review",
            style = MaterialTheme.typography.body1,
            color = Color.Gray
        )

        val context = LocalContext.current
        val calendar = Calendar.getInstance()

        var userName by remember { mutableStateOf("") }
        var rating by remember { mutableStateOf("") }
        var desc by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }

        val maxRate = 10

        // Fetching current year, month and day
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                date = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            }, year, month, dayOfMonth
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(end = 4.dp),
                value = userName,
                label = {
                    Text(
                        text = "Your Name"
                    )
                },
                onValueChange = {
                    userName = it
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                value = rating,
                label = {
                    Text(
                        text = "Rate (0-10)"
                    )
                },
                onValueChange = {
                    if (it.isNotEmpty()) {
                        if (it.toInt() <= maxRate) rating = it
                    } else
                        rating = it
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = desc,
            label = {
                Text(
                    text = "Describe your experience"
                )
            },
            onValueChange = {
                desc = it
            },
            singleLine = false,
            maxLines = 3,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp),
            onClick = { datePicker.show() }
        ) {
            Icon(
                painter = painterResource(
                    R.drawable.ic_date_18
                ),
                contentDescription = "Date"
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = date.ifEmpty {
                    "When was it?"
                }
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onClick = {
                onReviewSubmit(userName, date, rating, desc)
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Submit Review"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CoffeeDetailReviewViewPreview() {
    CoffeeDetailReviewView(
        onReviewSubmit = { _, _, _, _ -> }
    )
}

