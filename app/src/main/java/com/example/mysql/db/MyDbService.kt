package com.example.mysql.db

import com.example.mysql.models.Group
import com.example.mysql.models.Students

interface MyDbService {
    fun addGroup(group: Group)
    fun showGroup():List<Group>
    fun getGroupById(id:Int):Group
    fun editGroup(group: Group):Int
    fun deleteGroup(group: Group)

    fun deleteStudentByGroupId(group: Group)
    fun addStudent(students: Students)
    fun showStudent():List<Students>
    fun editStudent(students: Students):Int
    fun deleteStudent(students: Students)
}