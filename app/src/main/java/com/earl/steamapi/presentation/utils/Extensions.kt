package com.earl.steamapi.presentation.utils

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.earl.steamapi.domain.models.SteamGame

object Extensions {

    fun EditText.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(500, 1500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {

                        afterTextChanged.invoke(editable.toString())
                    }
                }.start()
            }
        })
    }

    fun List<SteamGame>.filterByText(text: String): List<SteamGame> {
        return if (text != "") {
            val textToSearch = text.lowercase()
            this.filter {
                it.appid.toString().lowercase().contains(textToSearch) || it.name.lowercase().contains(textToSearch) ||
                        it.appid.toString().lowercase().startsWith(textToSearch) || it.name.lowercase().startsWith(textToSearch)
            }
        } else {
            this
        }
    }

}