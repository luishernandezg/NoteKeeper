package com.example.notekeeper

import android.os.Bundle
import androidx.lifecycle.ViewModel

class ItemsActivityViewModel: ViewModel()  {

    var isNewlyCreated = true

    var navDrawerDisplaySelection = R.id.nav_notes
    val NAV_DRAWER_SELECTION_NAME_KEY = "NAV_DRAWER_SELECTION_NAME_KEY"
    val RECENTLY_VIEWED_NOTE_IDS_NAME_KEY = "RECENTLY_VIEWED_NOTE_LIST_KEY"

    private val maxRecentlyViewedNotes = 5
    val recentlyViewedNotes = ArrayList<NoteInfo>(maxRecentlyViewedNotes)

    fun addToRecentlyViewedNotes(note: NoteInfo) {
        // Check if selection is already in the list
        val existingIndex = recentlyViewedNotes.indexOf(note)
        if (existingIndex == -1) {
            // it isn't in the list...
            // Add new one to beginning of list and remove any beyond max we want to keep
            recentlyViewedNotes.add(0, note)
            for (index in recentlyViewedNotes.lastIndex downTo maxRecentlyViewedNotes)
                recentlyViewedNotes.removeAt(index)
        } else {
            // it is in the list...
            // Shift the ones above down the list and make it first member of the list
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewedNotes[index + 1] = recentlyViewedNotes[index]
            recentlyViewedNotes[0] = note
        }
    }

    fun saveState(outState: Bundle) {
        outState.putInt(NAV_DRAWER_SELECTION_NAME_KEY,navDrawerDisplaySelection)
        val notesIds = DataManager.noteIdsAsIntArray(recentlyViewedNotes)
        outState.putIntArray(RECENTLY_VIEWED_NOTE_IDS_NAME_KEY,notesIds)
    }

    fun restoreState(savedInstanceState: Bundle) {
        navDrawerDisplaySelection = savedInstanceState.getInt(NAV_DRAWER_SELECTION_NAME_KEY)
        val notesIds = savedInstanceState.getIntArray(RECENTLY_VIEWED_NOTE_IDS_NAME_KEY)
        val noteList = notesIds?.let { DataManager.loadNotes(*it) }
        noteList?.let { recentlyViewedNotes.addAll(it) }
    }
}