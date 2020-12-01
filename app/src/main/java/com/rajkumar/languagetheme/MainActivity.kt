package com.rajkumar.languagetheme

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.Group
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rajkumar.languagetheme.databinding.ActivityMainBinding
import com.rajkumar.languagetheme.databinding.DarkThemeBottomSheetBinding
import com.rajkumar.languagetheme.databinding.LanguageBottomSheetBinding

class MainActivity : BaseActivity() {

    private lateinit var session: SessionManager

    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        session = SessionManager(this)
        when {
            session.getLanguage() == "en" -> activityBinding.textViewSelectedLanguage.text =
                resources.getString(R.string.english)
            session.getLanguage() == "ta" -> activityBinding.textViewSelectedLanguage.text =
                resources.getString(R.string.tamil)
            session.getLanguage() == "hi" -> activityBinding.textViewSelectedLanguage.text =
                resources.getString(R.string.hindi)
        }

        activityBinding.groupTheme.setAllOnClickListener {

            val darkThemeBinding: DarkThemeBottomSheetBinding = DarkThemeBottomSheetBinding.bind(
                View.inflate(
                    this,
                    R.layout.dark_theme_bottom_sheet,
                    null
                )
            )

            val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
            dialog.setContentView(darkThemeBinding.root)
            dialog.show()

            when (session.getDisplayMode()) {
                "light" ->
                    darkThemeBinding.radioGroupTheme.check(R.id.radioButtonLight)
                "dark" ->
                    darkThemeBinding.radioGroupTheme.check(R.id.radioButtonDark)
            }

            darkThemeBinding.radioGroupTheme.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radioButtonLight -> {
                        session.setDisplayMode("light")
                    }
                    R.id.radioButtonDark -> {
                        session.setDisplayMode("dark")
                    }

                }
                recreate()
                dialog.dismiss()
            }
        }

        activityBinding.groupLanguage.setAllOnClickListener {

            val languageBinding: LanguageBottomSheetBinding = LanguageBottomSheetBinding.bind(
                View.inflate(
                    this,
                    R.layout.language_bottom_sheet,
                    null
                )
            )
            val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
            dialog.setContentView(languageBinding.root)
            dialog.show()
            when {
                session.getLanguage() == "en" -> languageBinding.radioGroupLanguage.check(R.id.radioButtonEnglish)
                session.getLanguage() == "ta" -> languageBinding.radioGroupLanguage.check(R.id.radioButtonTamil)
                session.getLanguage() == "hi" -> languageBinding.radioGroupLanguage.check(R.id.radioButtonHindi)
            }

            languageBinding.radioGroupLanguage.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radioButtonEnglish -> {
                        session.setLanguage("en")
                        LocaleHelper.setLocale(this, "en")//Solution
                    }
                    R.id.radioButtonTamil -> {
                        session.setLanguage("ta")
                        LocaleHelper.setLocale(this, "ta")//Solution
                    }
                    R.id.radioButtonHindi -> {
                        session.setLanguage("hi")
                        LocaleHelper.setLocale(this, "hi")//Solution
                    }
                }
                recreate()
                dialog.dismiss()
            }
        }

        activityBinding.button.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity2::class.java
                )
            )
        }
    }

    private fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }

    override fun onResume() {
        super.onResume()
        if (session.getDisplayMode() == "light") {
            activityBinding.textViewSelectedMode.text = resources.getText(R.string.light)
        } else if (session.getDisplayMode() == "dark") {
            activityBinding.textViewSelectedMode.text = resources.getText(R.string.dark)
        }
    }
}