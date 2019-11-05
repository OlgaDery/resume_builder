package com.google.resumemaker.ui.records

import android.content.res.Resources
import com.google.resumemaker.R
import com.google.resumemaker.entity.BaseRecord
import com.google.resumemaker.entity.Education
import java.lang.reflect.Type
//This class helps to configure the views for the implementations of BaseRecord class (to set up the headers,
// visible fields etc).
class RecordFragmentMode (val modeName: String, val label: String, val data: MutableList<out BaseRecord>,
                          val type: Type, val res: Resources) {

    val titleHeader: String
        get() {
            return res.getString(R.string.edit_record_title_general)
        }

    val achievementLevelHeader: String
    get() {
        return when (type) {
            Education::class.java -> res.getString(R.string.edit_edu_achievement)
            else -> res.getString(R.string.edit_position_achievement)
        }
    }

    val organizationHeader: String
        get() {
            return when (type) {
                Education::class.java -> res.getString(R.string.edit_organization_edu)
                else -> res.getString(R.string.edit_organization_position)
            }
        }

    val showFaculty: Boolean
        get() {
            return when (type) {
                Education::class.java -> true
                else -> false
            }
        }

}