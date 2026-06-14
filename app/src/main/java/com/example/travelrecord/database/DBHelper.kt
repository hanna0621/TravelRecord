package com.example.travelrecord.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.travelrecord.model.Travel

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "travel.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_TRAVEL = "travel"
        const val COLUMN_NO = "no"
        const val COLUMN_PLACE = "place"
        const val COLUMN_VISIT_DATE = "visit_date"
        const val COLUMN_MEMO = "memo"
        const val COLUMN_PHOTO_URI = "photo_uri"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSql = """
            CREATE TABLE $TABLE_TRAVEL (
                $COLUMN_NO INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PLACE TEXT NOT NULL,
                $COLUMN_VISIT_DATE TEXT NOT NULL,
                $COLUMN_MEMO TEXT,
                $COLUMN_PHOTO_URI TEXT
            )
        """.trimIndent()

        db.execSQL(createTableSql)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRAVEL")
        onCreate(db)
    }

    // 1. 여행 기록 추가
    fun insertTravel(travel: Travel): Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_PLACE, travel.place)
            put(COLUMN_VISIT_DATE, travel.visitDate)
            put(COLUMN_MEMO, travel.memo)
            put(COLUMN_PHOTO_URI, travel.photoUri)
        }

        val result = db.insert(TABLE_TRAVEL, null, values)
        db.close()

        return result
    }

    // 2. 여행 기록 전체 조회
    fun getAllTravels(): MutableList<Travel> {
        val travelList = mutableListOf<Travel>()
        val db = readableDatabase

        val sql = """
            SELECT * FROM $TABLE_TRAVEL
            ORDER BY $COLUMN_VISIT_DATE DESC
        """.trimIndent()

        val cursor = db.rawQuery(sql, null)

        cursor.use {
            while (it.moveToNext()) {
                val travel = Travel(
                    no = it.getInt(it.getColumnIndexOrThrow(COLUMN_NO)),
                    place = it.getString(it.getColumnIndexOrThrow(COLUMN_PLACE)),
                    visitDate = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_VISIT_DATE)
                    ),
                    memo = it.getString(it.getColumnIndexOrThrow(COLUMN_MEMO))
                        ?: "",
                    photoUri = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_PHOTO_URI)
                    )
                )

                travelList.add(travel)
            }
        }

        db.close()
        return travelList
    }

    fun getAllTravelsOldest(): MutableList<Travel> {
        val travelList = mutableListOf<Travel>()
        val db = readableDatabase

        val sql = """
            SELECT * FROM $TABLE_TRAVEL
            ORDER BY $COLUMN_VISIT_DATE ASC
        """.trimIndent()

        val cursor = db.rawQuery(sql, null)

        cursor.use {
            while (it.moveToNext()) {
                val travel = Travel(
                    no = it.getInt(it.getColumnIndexOrThrow(COLUMN_NO)),
                    place = it.getString(it.getColumnIndexOrThrow(COLUMN_PLACE)),
                    visitDate = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_VISIT_DATE)
                    ),
                    memo = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_MEMO)
                    ) ?: "",
                    photoUri = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_PHOTO_URI)
                    )
                )

                travelList.add(travel)
            }
        }

        db.close()
        return travelList
    }

    // 3. 특정 여행 기록 조회
    fun getTravelById(no: Int): Travel? {
        val db = readableDatabase

        val cursor = db.query(
            TABLE_TRAVEL,
            null,
            "$COLUMN_NO = ?",
            arrayOf(no.toString()),
            null,
            null,
            null
        )

        var travel: Travel? = null

        cursor.use {
            if (it.moveToFirst()) {
                travel = Travel(
                    no = it.getInt(it.getColumnIndexOrThrow(COLUMN_NO)),
                    place = it.getString(it.getColumnIndexOrThrow(COLUMN_PLACE)),
                    visitDate = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_VISIT_DATE)
                    ),
                    memo = it.getString(it.getColumnIndexOrThrow(COLUMN_MEMO))
                        ?: "",
                    photoUri = it.getString(
                        it.getColumnIndexOrThrow(COLUMN_PHOTO_URI)
                    )
                )
            }
        }

        db.close()
        return travel
    }

    // 4. 여행 기록 수정
    fun updateTravel(travel: Travel): Int {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_PLACE, travel.place)
            put(COLUMN_VISIT_DATE, travel.visitDate)
            put(COLUMN_MEMO, travel.memo)
            put(COLUMN_PHOTO_URI, travel.photoUri)
        }

        val result = db.update(
            TABLE_TRAVEL,
            values,
            "$COLUMN_NO = ?",
            arrayOf(travel.no.toString())
        )

        db.close()
        return result
    }

    // 5. 여행 기록 한 개 삭제
    fun deleteTravel(no: Int): Int {
        val db = writableDatabase

        val result = db.delete(
            TABLE_TRAVEL,
            "$COLUMN_NO = ?",
            arrayOf(no.toString())
        )

        db.close()
        return result
    }

    // 6. 여행 기록 전체 삭제
    fun deleteAllTravels(): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_TRAVEL, null, null)

        db.close()
        return result
    }
}