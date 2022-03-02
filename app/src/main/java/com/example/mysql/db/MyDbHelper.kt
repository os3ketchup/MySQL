package com.example.mysql.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.FileObserver.CREATE
import com.example.mysql.models.Group
import com.example.mysql.models.Students

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDbService {

    companion object {
        const val DB_NAME = "dbc6"
        const val DB_VERSION = 1

        const val TABLE_GROUP = "groups"
        const val GROUP_ID = "id"
        const val GROUP_NAME = "name"

        const val TABLE_STUDENT = "students"
        const val STUDENT_ID = "id"
        const val STUDENT_NAME = "name"
        const val STUDENT_NUMBER = "number"
        const val STUDENT_GROUP_ID = "group_id"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val groupQuery =
            "CREATE TABLE $TABLE_GROUP ($GROUP_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $GROUP_NAME text not null)"
        val studentQuery =
            "CREATE TABLE $TABLE_STUDENT ($STUDENT_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $STUDENT_NAME text not null, $STUDENT_NUMBER TEXT NOT NULL, $STUDENT_GROUP_ID INTEGER NOT NULL, FOREIGN KEY ($STUDENT_GROUP_ID) REFERENCES $TABLE_GROUP ($GROUP_ID))"


        p0?.execSQL(groupQuery)
        p0?.execSQL(studentQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun addGroup(group: Group) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_NAME, group.name)
        database.insert(TABLE_GROUP, null, contentValues)
        database.close()
    }

    override fun showGroup(): List<Group> {
        val list = ArrayList<Group>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_GROUP"

        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val group = Group(
                    cursor.getInt(0),
                    cursor.getString(1)
                )
                list.add(group)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getGroupById(id: Int): Group {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_GROUP,
            arrayOf(
                GROUP_ID,
                GROUP_NAME
            ),
            "$GROUP_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val group = Group(
            cursor.getInt(0),
            cursor.getString(1)
        )
        return group
    }

    override fun editGroup(group: Group): Int {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(group: Group) {
        deleteStudentByGroupId(group)
        val database = this.writableDatabase
        database.delete(TABLE_GROUP, "$GROUP_ID =?", arrayOf(group.id.toString()) )
        database.close()
    }

    override fun addStudent(students: Students) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, students.name)
        contentValues.put(STUDENT_NUMBER, students.number)
        contentValues.put(STUDENT_GROUP_ID, students.group?.id)
        database.insert(TABLE_STUDENT, null, contentValues)
        database.close()
    }

    override fun showStudent(): List<Students> {
        val database = this.readableDatabase
        val list = ArrayList<Students>()
        val query = "select * from $TABLE_STUDENT"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                    val student = Students(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        getGroupById(cursor.getInt(3))
                    )
                list.add(student)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun editStudent(students: Students): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_ID, students.id)
        contentValues.put(STUDENT_NAME, students.name)
        contentValues.put(STUDENT_NUMBER, students.number)

        return database.update(TABLE_STUDENT, contentValues, "$STUDENT_ID = ?", arrayOf(students.id.toString()))
    }

    override fun deleteStudent(students: Students) {

       val database = this.writableDatabase
        database.delete(TABLE_STUDENT, "$STUDENT_ID =?", arrayOf(students.id.toString()) )
        database.close()
    }

    override fun deleteStudentByGroupId(group: Group) {
        val query = "select * from $TABLE_STUDENT where $STUDENT_GROUP_ID = ${group.id}"
        val database = this.writableDatabase
       val cursor =  database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                val students = Students(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    getGroupById(cursor.getInt(3))
                )
                deleteStudent(students)
            }while (cursor.moveToNext())
        }

    }
}