package ru.averkiev.budget.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri.parse
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.averkiev.budget.R
import ru.averkiev.budget.databinding.FragmentSettingsBinding
import ru.averkiev.budget.models.CharacterResponse
import ru.averkiev.budget.repositories.KtorNetwork
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding ?: throw Exception()

    private val THEME_KEY = "theme"
    private val NOTIFICATIONS_KEY = "notifications"
    private val LANGUAGE_KEY = "language"
    private val filename = "first_v"
    private val network = KtorNetwork()

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateRestoreButtonVisibility()

        binding.btnRestoreFile.setOnClickListener {
            restoreFileFromBackup(requireContext(), filename)
        }

        val ifFileExist = checkIfFileExists(requireContext(), filename)
        if (ifFileExist) {
            binding.tvFileStatus.text = "Файл существует"
            binding.btnDeleteFile.visibility = View.VISIBLE
        } else {
            binding.tvFileStatus.text = "Файл не существует"
            binding.btnDeleteFile.visibility = View.GONE
        }

        binding.btnDeleteFile.setOnClickListener {
            backupFileToInternalStorage(requireContext(), filename)
            this.deleteFile(requireContext(), filename)
            binding.tvFileStatus.text = "Файл удален"
            binding.btnDeleteFile.visibility = View.GONE
            updateRestoreButtonVisibility()
        }

        var characters: List<CharacterResponse> = listOf()
        lifecycleScope.launch {
            characters = network.getCharacters(50, 1)
            saveHeroesToFile(requireContext(), characters, filename)
            Log.d("SettingsFragment","characters: $characters")
        }


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        loadSettings()

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            saveThemeSetting(isChecked)

            if (isChecked) {
                (requireActivity() as AppCompatActivity).delegate.localNightMode =
                    AppCompatDelegate.MODE_NIGHT_YES
            } else {
                (requireActivity() as AppCompatActivity).delegate.localNightMode =
                    AppCompatDelegate.MODE_NIGHT_NO
            }
        }

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationsSetting(isChecked)
        }

        binding.btnPrevSettings.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_OnBoardFragment)
        }

        val languages = listOf("English", "Русский", "Español")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerLanguage.adapter = adapter

        lifecycleScope.launch {
            val currentLanguage = loadLanguageSettings()
            val languagePosition = languages.indexOf(currentLanguage)
            binding.spinnerLanguage.setSelection(if (languagePosition != -1) languagePosition else 0)
        }

        binding.spinnerLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedLanguage = languages[position]
                    lifecycleScope.launch {
                        saveLanguageSettings(selectedLanguage)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    private val LANGUAGE_PREFERENCE_KEY = stringPreferencesKey(LANGUAGE_KEY)

    private fun loadSettings() {
        val isDarkTheme = sharedPreferences.getBoolean(THEME_KEY, false)
        binding.switchTheme.isChecked = isDarkTheme;

        val areNotificationsEnabled = sharedPreferences.getBoolean(NOTIFICATIONS_KEY, true)
        binding.switchNotifications.isChecked = areNotificationsEnabled
    }

    private suspend fun loadLanguageSettings(): String {
        val preferences = context?.dataStore?.data?.first()
        return preferences?.get(LANGUAGE_PREFERENCE_KEY) ?: "English"
    }

    private suspend fun saveLanguageSettings(language: String) {
        context?.dataStore?.edit { preferences ->
            preferences[LANGUAGE_PREFERENCE_KEY] = language
        }
    }

    private fun saveThemeSetting(isDark: Boolean) {
        sharedPreferences.edit().putBoolean(THEME_KEY, isDark).apply()
    }

    private fun saveNotificationsSetting(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(NOTIFICATIONS_KEY, isEnabled).apply()
    }

    fun saveHeroesToFile(context: Context, heroes: List<CharacterResponse>, filename: String) {
        Log.d("HeroesSize", "Heroes size: ${heroes.size}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = parse("package:${requireContext().packageName}")
                startActivity(intent)
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                100
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    "$filename.txt"
                )

                try {
                    val jsonString = Json.encodeToString(heroes)
                    FileOutputStream(file).use { fos ->
                        OutputStreamWriter(fos).use { writer ->
                            writer.write(jsonString)
                        }
                    }
                    Log.d("FileSave", "File saved successfully: ${file.absolutePath}")
                } catch (ioEx: IOException) {
                    Log.e("FileSave", "Error writing file: ${ioEx.message}")
                }
            } else {
                Log.e("FileSave", "Permission to access external storage is not granted.")
            }
        } else {
            val file =
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "$filename.txt")
            try {
                val jsonString = Json.encodeToString(heroes)
                FileOutputStream(file).use { fos ->
                    OutputStreamWriter(fos).use { writer ->
                        writer.write(jsonString)
                    }
                }
                Log.d("FileSave", "File saved successfully: ${file.absolutePath}")
            } catch (e: IOException) {
                Log.e("FileSave", "Error writing file: ${e.message}")
            }
        }
    }

    private fun backupFileToInternalStorage(context: Context, fileName: String) {
        val externalFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "$fileName.txt"
        )

        if (!externalFile.exists()) {
            Log.e("Backup", "File does not exist in external storage.")
            return
        }

        val internalFile = File(context.filesDir, "${fileName}_backup.txt")

        try {
            externalFile.inputStream().use { input ->
                internalFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            Log.d("Backup", "File backed up successfully to internal storage.")
        } catch (e: IOException) {
            Log.e("Backup", "Failed to backup file: ${e.message}")
        }
    }

    private fun checkBackupFileExists(context: Context, fileName: String): Boolean {
        val internalFile = File(context.filesDir, "${fileName}_backup.txt")
        return internalFile.exists()
    }

    private fun updateRestoreButtonVisibility() {
        val backupExists = checkBackupFileExists(requireContext(), filename)
        if (backupExists) {
            binding.btnRestoreFile.visibility = View.VISIBLE
            binding.tvBackupStatus.text = "Резервная копия существует"
        } else {
            binding.btnRestoreFile.visibility = View.GONE
            binding.tvBackupStatus.text = "Резервной копии нет"
        }
    }

    private fun restoreFileFromBackup(context: Context, fileName: String) {
        val internalFile = File(context.filesDir, "${fileName}_backup.txt")
        if (!internalFile.exists()) {
            Log.e("Restore", "Backup file does not exist.")
            return
        }

        val externalFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "$fileName.txt"
        )

        try {
            internalFile.inputStream().use { input ->
                externalFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            Log.d("Restore", "File restored successfully to external storage.")
            updateRestoreButtonVisibility()
            binding.tvFileStatus.text = "Файл восстановлен"
            binding.btnDeleteFile.visibility = View.VISIBLE
        } catch (e: IOException) {
            Log.e("Restore", "Failed to restore file: ${e.message}")
        }
    }

    fun loadHeroesFromFile(context: Context, fileName: String): List<CharacterResponse> {
        val file =
            File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "$fileName.txt")
        if (file.exists()) {
            try {
                val jsonString = file.readText()

                return Json.decodeFromString(jsonString)
            } catch (e: IOException) {
                Log.e("FileLoad", "Error reading file: ${e.message}")
            } catch (e: Exception) {
                Log.e("FileLoad", "Error parsing JSON: ${e.message}")
            }
        } else {
            Log.e("FileLoad", "File does not exist.")
        }
        return emptyList()
    }

    private fun checkIfFileExists(context: Context, fileName: String): Boolean {
        val file =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "$fileName.txt")
        return file.exists()
    }

    fun deleteFile(context: Context, fileName: String) {
        val file =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "$fileName.txt")
        if (file.exists()) {
            if (file.delete()) {
                Log.d("FileDelete", "File deleted successfully.")
            } else {
                Log.e("FileDelete", "Failed to delete the file.")
            }
        } else {
            Log.e("FileDelete", "File does not exist.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}