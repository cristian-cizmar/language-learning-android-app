package com.cristiancizmar.learnalanguage.presentation.feature.words

import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import com.cristiancizmar.learnalanguage.domain.Word
import com.cristiancizmar.learnalanguage.presentation.feature.MainDispatcherRule
import com.cristiancizmar.learnalanguage.presentation.feature.words.WordsViewModel.SORT
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.reflect.Whitebox

@RunWith(MockitoJUnitRunner::class)
class WordsViewModelTest {

    private var fileWordsRepository: FileWordsRepository = mock()
    private lateinit var viewModel: WordsViewModel

    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = WordsViewModel(dispatcher, fileWordsRepository)
    }

    @Test
    fun setMinWords() = runTest {
        val words = 5

        viewModel.onAction(WordsViewModel.WordsEvent.SetMinWords(words))

        Assert.assertEquals(words, viewModel.state.minWords)
    }

    @Test
    fun setMaxWords() = runTest {
        val words = 5

        viewModel.onAction(WordsViewModel.WordsEvent.SetMaxWords(words))

        Assert.assertEquals(words, viewModel.state.maxWords)
    }

    @Test
    fun updateSort() = runTest {
        val sort = SORT.PERC

        viewModel.onAction(WordsViewModel.WordsEvent.UpdateSort(sort))

        Assert.assertEquals(sort, viewModel.state.sort)
    }

    @Test
    fun setSelectedWord() = runTest {
        val word: Word = mock()

        viewModel.onAction(WordsViewModel.WordsEvent.SetSelectedWord(word))

        Assert.assertEquals(word, viewModel.state.selectedWord)
    }

    @Test
    fun setWordNote() = runTest {
        val note = "note"
        val idx = 5
        val word = Word(idx, "", "", "", "", 0, 0, 1, "")
        viewModel.onAction(WordsViewModel.WordsEvent.SetSelectedWord(word))

        viewModel.onAction(WordsViewModel.WordsEvent.SetWordNote(note))

        Assert.assertEquals(note, viewModel.state.selectedWord?.note)
        verify(fileWordsRepository).setWordNote(idx, note)
    }

    @Test
    fun removeSelectedWord() = runTest {
        val word: Word = mock()
        viewModel.onAction(WordsViewModel.WordsEvent.SetSelectedWord(word))
        Assert.assertEquals(word, viewModel.state.selectedWord)

        viewModel.onAction(WordsViewModel.WordsEvent.RemoveSelectedWord)

        Assert.assertNull(viewModel.state.selectedWord)
    }

    @Test
    fun showSearchPopup() = runTest {
        viewModel.onAction(WordsViewModel.WordsEvent.ShowSearchPopup)

        Assert.assertTrue(viewModel.state.showSearchPopup)
    }

    @Test
    fun hideSearchPopup() = runTest {
        viewModel.onAction(WordsViewModel.WordsEvent.ShowSearchPopup)
        Assert.assertTrue(viewModel.state.showSearchPopup)

        viewModel.onAction(WordsViewModel.WordsEvent.HideSearchPopup)

        Assert.assertFalse(viewModel.state.showSearchPopup)
    }

    @Test
    fun updateSearch() = runTest {
        val search = "search"
        Whitebox.setInternalState(viewModel, "allWords", emptyList<Word>())

        viewModel.onAction(WordsViewModel.WordsEvent.UpdateSearch(search))

        Assert.assertEquals(search, viewModel.state.searchText)
    }

    @Test
    fun updateWordDifficulty_1to2() = runTest {
        val newDifficulty = 2
        val idx = 5
        val word = Word(idx, "", "", "", "", 0, 0, 1, "")
        viewModel.onAction(WordsViewModel.WordsEvent.SetSelectedWord(word))

        viewModel.onAction(WordsViewModel.WordsEvent.UpdateWordDifficulty)

        Assert.assertEquals(newDifficulty, viewModel.state.selectedWord?.difficulty)
        verify(fileWordsRepository).setWordDifficulty(idx, newDifficulty)
    }

    @Test
    fun updateWordDifficulty_1to3() = runTest {
        val newDifficulty = 3
        val idx = 5
        val word = Word(idx, "", "", "", "", 0, 0, 1, "")
        viewModel.onAction(WordsViewModel.WordsEvent.SetSelectedWord(word))

        repeat(2) {
            viewModel.onAction(WordsViewModel.WordsEvent.UpdateWordDifficulty)
        }

        Assert.assertEquals(newDifficulty, viewModel.state.selectedWord?.difficulty)
        verify(fileWordsRepository).setWordDifficulty(idx, newDifficulty)
    }

    @Test
    fun updateWordDifficulty_1to4() = runTest {
        val newDifficulty = 4
        val idx = 5
        val word = Word(idx, "", "", "", "", 0, 0, 1, "")
        viewModel.onAction(WordsViewModel.WordsEvent.SetSelectedWord(word))

        repeat(3) {
            viewModel.onAction(WordsViewModel.WordsEvent.UpdateWordDifficulty)
        }

        Assert.assertEquals(newDifficulty, viewModel.state.selectedWord?.difficulty)
        verify(fileWordsRepository).setWordDifficulty(idx, newDifficulty)
    }

    @Test
    fun updateWordDifficulty_1to4to1() = runTest {
        val newDifficulty = 1
        val idx = 5
        val word = Word(idx, "", "", "", "", 0, 0, 1, "")
        viewModel.onAction(WordsViewModel.WordsEvent.SetSelectedWord(word))

        repeat(4) {
            viewModel.onAction(WordsViewModel.WordsEvent.UpdateWordDifficulty)
        }

        Assert.assertEquals(newDifficulty, viewModel.state.selectedWord?.difficulty)
        verify(fileWordsRepository).setWordDifficulty(idx, newDifficulty)
    }
}