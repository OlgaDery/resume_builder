package com.google.resumemaker
import com.google.resumemaker.entity.*
import com.google.resumemaker.ui.records.RecordFragmentMode
import com.google.resumemaker.ui.records.RecordsFragment
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import io.mockk.*
import org.mockito.MockitoAnnotations


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest: BaseUnitTest() {

    private lateinit var viewModel: MainViewModel
    private lateinit var modeEdu: RecordFragmentMode
    private lateinit var modePositions: RecordFragmentMode

    @Before
    fun setup () {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel()
        viewModel.preferences = preferences
        `when`(mockResources.getString(R.string.menu_education)).thenReturn("Education")
        `when`(mockResources.getString(R.string.menu_position)).thenReturn("Positions")
        val resume = Resume(Profile())
        viewModel.resume = resume

        modeEdu = RecordFragmentMode(
            RecordsFragment.EDU_MODE, mockResources.getString(R.string.menu_education), resume.educations,
            Education::class.java, mockResources)

        modePositions = RecordFragmentMode(
            RecordsFragment.POSITION_MODE, mockResources.getString(R.string.menu_position), resume.positions,
            Education::class.java, mockResources)
    }

    @Test
    fun test_setUpNewRecord_assert_proper_class_creation() {
        val record = viewModel.setUpNewRecord(mode = modeEdu)
        assertTrue(record is Education)

        val record1 = viewModel.setUpNewRecord(mode = modePositions)
        assertTrue(record1 is Position)

        val record2 = viewModel.setUpNewRecord(record = Education(profileID = viewModel.resume!!.profile.id), mode = modePositions)
        assertNull(record2)
    }

    @Test
    fun test_addOrRemoveRecord_assert_record_added_and_removed() {
        val position = Position(profileID = viewModel.resume!!.profile.id)
        position.endDate = "05/06/18".toDate(MainActivity.DATE_FORMAT)

        val position1 = Position(profileID = viewModel.resume!!.profile.id)
        position1.endDate = "05/06/14".toDate(MainActivity.DATE_FORMAT)

        val position2 = Position(profileID = viewModel.resume!!.profile.id)
        position2.endDate = "07/06/19".toDate(MainActivity.DATE_FORMAT)

        viewModel.modifyRecordsList(record = position, mode = modePositions, shouldAdd = true, shouldRemove = false)
        viewModel.modifyRecordsList(record = position1, mode = modePositions, shouldAdd = true, shouldRemove = false)
        viewModel.modifyRecordsList(record = position2, mode = modePositions, shouldAdd = true, shouldRemove = false)

        assertTrue(modePositions.data.size == 3)
        assertTrue(viewModel.resume!!.positions.size == 3)

        viewModel.modifyRecordsList(record = position1, mode = modePositions, shouldAdd = false, shouldRemove = true)

        assertTrue(modePositions.data.size == 2)
        assertTrue(viewModel.resume!!.positions.size == 2)
        assertFalse(viewModel.resume!!.positions.contains(position1))

        assertTrue(viewModel.resume!!.positions[0] == position2)
        assertTrue(viewModel.resume!!.positions[1] == position)
    }

    @Test
    fun test_cancelCollectionUpdate_assert_record_replacedWithInitialRevision() {
        val position = Position(profileID = viewModel.resume!!.profile.id)
        position.endDate = "05/06/18".toDate(MainActivity.DATE_FORMAT)

        val position1 = Position(profileID = viewModel.resume!!.profile.id)
        position1.endDate = "05/06/14".toDate(MainActivity.DATE_FORMAT)

        viewModel.modifyRecordsList(record = position, mode = modePositions, shouldAdd = true, shouldRemove = false)
        viewModel.modifyRecordsList(record = position1, mode = modePositions, shouldAdd = true, shouldRemove = false)

        val reservedCopy = position1.copyClassFields()

        position1.endDate = "01/01/01".toDate(MainActivity.DATE_FORMAT)
        val settingBackToList = viewModel.cancelCollectionUpdate(modePositions, reservedCopy)
        assertTrue(settingBackToList == reservedCopy)
        assertTrue(viewModel.resume!!.positions.size == 2)
        assertTrue(viewModel.resume!!.positions[1].endDateText == "05/06/14")
    }

    @Test
    fun test_createCopyOfRecord_assert_copy_created() {
        val position = Position(profileID = viewModel.resume!!.profile.id)
        position.endDate = "05/06/18".toDate(MainActivity.DATE_FORMAT)
        viewModel.createCopyOfRecord(position)
        assertTrue(position == viewModel.copyOfRecordToModify)
        assertTrue(viewModel.copyOfRecordToModify!!.endDate == position.endDate)
    }

    @Test
    //Using the mockk library to test kotlin coroutines
    fun test_shareFile_isRunning() {
        val viewModel = mockk<MainViewModel>()
        coEvery {
            viewModel.shareFile("")
        }just Runs
        viewModel.shareFile("")
        coVerify {
            viewModel.shareFile("")
        }
    }


    @Test
    fun test_PreferencesGetValue() {
        val value = viewModel.preferences.getValue<Resume>("", Resume::class.java)
        assertNull(value)
    }

    @Test
    fun test_updateProfileOrCancelUpdate_update() {
        viewModel.resume!!.profile.firstName = "AAA"
        viewModel.updateProfileOrCancelUpdate(false)
        assertTrue(viewModel.resume!!.profile.firstName == "AAA")
    }

    @Test
    fun test_updateProfileOrCancelUpdate_Cancel() {
        viewModel.resume!!.profile.firstName = "AAA"
        viewModel.updateProfileOrCancelUpdate(false)
        assertTrue(viewModel.resume!!.profile.firstName == "AAA")
    }
}
