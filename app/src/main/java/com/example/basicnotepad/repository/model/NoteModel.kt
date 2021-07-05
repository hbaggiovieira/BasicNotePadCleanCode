package com.example.basicnotepad.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true) var noteId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("content") val content: String,
    @SerializedName("colorId") val colorId: Int
)