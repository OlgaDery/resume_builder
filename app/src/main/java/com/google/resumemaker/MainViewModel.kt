package com.google.resumemaker

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.resumemaker.entity.*
import com.google.resumemaker.providers.PreferencesImpl
import com.google.resumemaker.ui.records.RecordFragmentMode
import com.google.resumemaker.ui.records.RecordsFragment
import javax.inject.Inject

class MainViewModel: ViewModel() {

    companion object {
        const val RESUME_PREF_KEY = "resume"
    }

    @Inject
    lateinit var preferences: PreferencesImpl

    @Inject
    lateinit var appContext: Context

    var resume: Resume? = null
    var profileCopy: Profile? = null
    var copyOfRecordToModify: BaseRecord? = null

    var mode: RecordFragmentMode? = null

    var recordToEditOrCreate: BaseRecord? = null

    fun setUpNewRecord(record: BaseRecord? = null) {
        when (mode!!.modeName) {
            RecordsFragment.EDU_MODE -> {
                recordToEditOrCreate = if (record == null) {
                    Education()
                } else {
                    record as Education
                }
            }
            RecordsFragment.POSITION_MODE -> {
                recordToEditOrCreate = if (record == null) {
                    Position()
                } else {
                    record as Position
                }
            }
        }
    }

    val setUpCompleted = MutableLiveData<Int>()

    fun setUpProfile() {
        resume = preferences.getValue(key = RESUME_PREF_KEY, type = Resume::class.java) ?: Resume(Profile())
        resume!!.res = appContext.resources
        profileCopy = resume!!.profile.copyClassFields()
        setUpCompleted.value = 0
    }

    fun updateProfileOrCancelUpdate(shouldCancel: Boolean) {
        if (shouldCancel) {
            resume!!.profile = profileCopy!!.copyClassFields()
        } else {
            profileCopy = resume!!.profile.copyClassFields()
            //TODO async call
            preferences.setValue(RESUME_PREF_KEY, Resume::class.java, resume)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun addUpdateRemoveRecord(record: BaseRecord, mode: RecordFragmentMode, shouldUpdate: Boolean) {
        val valueExists = mode.data.contains(record)
        if (valueExists) {
            mode.data.remove(record)
        }
        if (shouldUpdate) {
            mode.data as MutableList<BaseRecord>
            mode.data.add(record)
        }
        preferences.setValue(RESUME_PREF_KEY, Resume::class.java, resume)
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
    fun cancelCollectionUpdate(mode: RecordFragmentMode, reservedCopy: BaseRecord) {
        mode.data as MutableList<BaseRecord>
        mode.data.remove(reservedCopy)
        mode.data.add(reservedCopy)
        recordToEditOrCreate = reservedCopy
    }

}