package com.google.resumemaker

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.resumemaker.entity.*
import com.google.resumemaker.providers.FileSystemImpl
import com.google.resumemaker.providers.PreferencesImpl
import com.google.resumemaker.ui.records.RecordFragmentMode
import com.google.resumemaker.ui.records.RecordsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.ClassCastException
import javax.inject.Inject

class MainViewModel: ViewModel() {

    companion object {
        const val RESUME_PREF_KEY = "resume"
    }
    @Inject
    lateinit var preferences: PreferencesImpl

    @Inject
    lateinit var fileSystem: FileSystemImpl

    @Inject
    lateinit var appContext: Context

    var resume: Resume? = null

    //Create the copies of entities to be modified. If the user considers to cancel modifications, these copies
    //will be used to restore the initial state of the objects.
    var profileCopy: Profile? = null
    var copyOfRecordToModify: BaseRecord? = null

    var mode: RecordFragmentMode? = null

    var recordToEditOrCreate: BaseRecord? = null

    val file = MutableLiveData<File>()

    fun setUpNewRecord(record: BaseRecord? = null, mode: RecordFragmentMode): BaseRecord? {

        when (mode.modeName) {
            RecordsFragment.EDU_MODE -> {
                return if (record == null) {
                    Education(profileID = resume!!.profile.id)
                } else {
                    try {
                        record as Education
                    } catch (e: ClassCastException) {
                        null
                    }
                }
            }
            RecordsFragment.POSITION_MODE -> {
                return if (record == null) {
                    Position(profileID = resume!!.profile.id)
                } else {
                    try {
                        record as Position
                    } catch (e: ClassCastException) {
                        null
                    }
                }
            }
        }
        return null
    }

    val setUpCompleted = MutableLiveData<Boolean>()

    fun setUpProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            resume = preferences.getValue(key = RESUME_PREF_KEY, type = Resume::class.java) ?: Resume(Profile())
            resume!!.res = appContext.resources
            profileCopy = resume!!.profile.copyClassFields()
            setUpCompleted.postValue(true)
        }
    }

    fun updateProfileOrCancelUpdate(shouldCancel: Boolean) {
        if (shouldCancel) {
            resume!!.profile = profileCopy!!.copyClassFields()
        } else {
            profileCopy = resume!!.profile.copyClassFields()
            preferences.setValue(RESUME_PREF_KEY, Resume::class.java, resume)
        }
    }

    fun addOrRemoveRecord(record: BaseRecord, mode: RecordFragmentMode, shouldAdd: Boolean, shouldRemove: Boolean) {
        modifyRecordsList(record, mode, shouldAdd, shouldRemove)
        preferences.setValue(RESUME_PREF_KEY, Resume::class.java, resume)
    }

    @Suppress("UNCHECKED_CAST")
    fun modifyRecordsList(record: BaseRecord, mode: RecordFragmentMode, shouldAdd: Boolean, shouldRemove: Boolean) {
        if (shouldRemove) {
            mode.data.remove(record)
            return
        }
        if (shouldAdd) {

            mode.data as MutableList<BaseRecord>
            if (mode.data.isEmpty()) {
                mode.data.add(record)
            } else {
                if(record.endDate == null) {
                    mode.data.add(0, record)
                } else {
                    var count = 0
                    val tempList = mutableListOf<BaseRecord>()
                    tempList.addAll(mode.data)
                    //Adding the records depending on their end date, so the list would be sorted.
                    for(rec in tempList) {
                        if (rec.endDate != null) {
                            if (rec.endDate!!.before(record.endDate)) {
                                mode.data.add(count, record)
                                break
                            }
                        }
                        if (mode.data.size-1 == count) {
                            mode.data.add(record)
                        }
                        count++
                    }
                }
            }
        }
    }

    fun createCopyOfRecord(recordToPreserve: BaseRecord) {
        var newRecord: BaseRecord? = null
        when (recordToPreserve) {
            is Education -> {
               newRecord = recordToPreserve.copyClassFields()
            }
            is Position -> {
                newRecord = recordToPreserve.copyClassFields()
            }
        }
        copyOfRecordToModify = newRecord
    }

    @Suppress("UNCHECKED_CAST")
    fun cancelCollectionUpdate(mode: RecordFragmentMode, reservedCopy: BaseRecord): BaseRecord {
        mode.data as MutableList<BaseRecord>
        var count = 0
        for (record in mode.data) {
            if (record == reservedCopy) {
                mode.data.removeAt(count)
                mode.data.add(count, reservedCopy)
                break
            }
            count++
        }
        return reservedCopy
    }

    fun shareFile (data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newFile = fileSystem.writeStringToFile(appContext, data.toByteArray(), "resume.txt")
            file.postValue(newFile)
        }
    }



}