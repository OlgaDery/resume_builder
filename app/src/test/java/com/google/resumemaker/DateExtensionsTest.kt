package com.google.resumemaker
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExtensionsTest: BaseUnitTest() {

    @Test
    fun test_extension_string_to_date () {
        val sampleData = arrayOf("05/06/18", "05/06/18")

        val date = sampleData[0].toDate(MainActivity.DATE_FORMAT)
        val date1 = sampleData[0].toDate(MainActivity.DATE_FORMAT)
        Assert.assertNotNull(date)
        Assert.assertTrue(date == date1)
    }

    @Test
    fun test_extension_date_to_sting () {
        val sampleData = "05/06/18"

        val date = sampleData.toDate(MainActivity.DATE_FORMAT)
        val string = date.toString(MainActivity.DATE_FORMAT)
        Assert.assertNotNull(string)
        Assert.assertTrue(sampleData == string)
    }


}