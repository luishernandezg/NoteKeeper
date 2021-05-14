package com.example.notekeeper

import android.provider.ContactsContract
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest{

    @Rule @JvmField
    val noteListActivity = ActivityTestRule(NoteListActivity::class.java)

    @Test
    fun createNewNote(){
        val course = DataManager.courses["android_async"]
        val noteTitle = "Test NoteTitle"
        val noteText = "This is the body of out test note"

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.spinnerCourses)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java),equalTo(course))).perform(click())

        onView(withId(R.id.textNoteTitle)).perform(typeText(noteTitle))
        onView(withId(R.id.textNoteText)).perform(typeText(noteText), closeSoftKeyboard())

        pressBack()

        val newNote = DataManager.notes.last()
        assertEquals(newNote.course,course)
        assertEquals(newNote.title, noteTitle)
        assertEquals(newNote.text,noteText)

    }
}