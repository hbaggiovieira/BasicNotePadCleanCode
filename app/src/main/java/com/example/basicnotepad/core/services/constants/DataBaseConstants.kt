package com.example.basicnotepad.core.services.constants

class DataBaseConstants private constructor(){

    object NOTE {
        const val TABLE_NAME = "Note"

        object COLUMNS {
            const val ID = "id"
            const val DESCRIPTION = "description"
            const val COLOR_ID = "colorId"
        }
    }


}