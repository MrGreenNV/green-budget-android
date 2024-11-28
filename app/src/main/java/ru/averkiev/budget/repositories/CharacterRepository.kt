package ru.averkiev.budget.repositories

import kotlinx.coroutines.flow.Flow
import ru.averkiev.budget.models.CharacterEntity
import ru.averkiev.budget.utils.CharacterDao

class CharacterRepository(private val characterDao: CharacterDao) {

    fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
    }

    suspend fun saveCharacters(characters: List<CharacterEntity>) {
        characterDao.insertCharacters(characters)
    }

    fun clearCharacters() {
        characterDao.deleteAll()
    }
}