package com.cristiancizmar.learnalanguage.presentation.feature.home

import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private var fileWordsRepository: FileWordsRepository = mock()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Mockito.`when`(fileWordsRepository.getFileNames()).thenReturn(emptyList())
        Mockito.`when`(fileWordsRepository.getMainText()).thenReturn("")
        Mockito.`when`(fileWordsRepository.switchLanguages).thenReturn(false)

        viewModel = HomeViewModel(fileWordsRepository)
    }

    @Test
    fun updateNotesText_anyText_textUpdated() {
        val newText = "new text"

        viewModel.onAction(HomeViewModel.HomeEvent.UpdateNotesText(newText))

        Assert.assertEquals(newText, viewModel.state.notesText)
    }

    @Test
    fun switchLanguages_isFalse_becomesTrue() {
        viewModel.onAction(HomeViewModel.HomeEvent.SwitchLanguages)

        Assert.assertTrue(viewModel.state.switchLanguages)
    }
}