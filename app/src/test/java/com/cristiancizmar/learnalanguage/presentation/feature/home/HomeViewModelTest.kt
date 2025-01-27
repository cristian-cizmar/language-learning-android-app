package com.cristiancizmar.learnalanguage.presentation.feature.home

import android.content.Context
import android.net.Uri
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private var fileWordsRepository: FileWordsRepository = mock()
    private var context: Context = mock()
    private var uri: Uri = mock()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Mockito.`when`(fileWordsRepository.getFileNames()).thenReturn(emptyList())
        Mockito.`when`(fileWordsRepository.getMainText()).thenReturn("")
        Mockito.`when`(fileWordsRepository.switchLanguages).thenReturn(false)
        Mockito.`when`(fileWordsRepository.getFavoriteFileName()).thenReturn("")

        viewModel = HomeViewModel(fileWordsRepository)
    }

    @Test
    fun importBackupFromFile() {
        viewModel.onAction(HomeViewModel.HomeEvent.ImportBackupFromFile(context, uri))

        verify(fileWordsRepository).importBackupFromFile(context, uri)
    }

    @Test
    fun updateNotesText_anyText_textUpdated() {
        val newText = "new text"

        viewModel.onAction(HomeViewModel.HomeEvent.UpdateNotesText(newText))

        Assert.assertEquals(newText, viewModel.state.notesText)
    }

    @Test
    fun updateSelectedFileName() {
        val fileName = "file.txt"

        viewModel.onAction(HomeViewModel.HomeEvent.UpdateSelectedFileName(fileName))

        Assert.assertEquals(fileName, viewModel.state.selectedFileName)
    }

    @Test
    fun updateFavoriteFileName() {
        val fileName = "file.txt"

        viewModel.onAction(HomeViewModel.HomeEvent.UpdateFavoriteFileName(fileName))

        Assert.assertEquals(fileName, viewModel.state.favoriteFileName)
    }

    @Test
    fun switchLanguages_isFalse_becomesTrue() {
        viewModel.onAction(HomeViewModel.HomeEvent.SwitchLanguages)

        Assert.assertTrue(viewModel.state.switchLanguages)
    }

    @Test
    fun showLanguagePopup() {
        viewModel.onAction(HomeViewModel.HomeEvent.ShowLanguagePopup)

        Assert.assertTrue(viewModel.state.showLanguagePopup)
    }

    @Test
    fun hideLanguagePopup() {
        viewModel.onAction(HomeViewModel.HomeEvent.ShowLanguagePopup)
        Assert.assertTrue(viewModel.state.showLanguagePopup)

        viewModel.onAction(HomeViewModel.HomeEvent.HideLanguagePopup)

        Assert.assertFalse(viewModel.state.showLanguagePopup)
    }

    @Test
    fun showDataPopup() {
        viewModel.onAction(HomeViewModel.HomeEvent.ShowDataPopup)

        Assert.assertTrue(viewModel.state.showDataPopup)
    }

    @Test
    fun hideDataPopup() {
        viewModel.onAction(HomeViewModel.HomeEvent.ShowDataPopup)
        Assert.assertTrue(viewModel.state.showDataPopup)

        viewModel.onAction(HomeViewModel.HomeEvent.HideDataPopup)

        Assert.assertFalse(viewModel.state.showDataPopup)
    }
}