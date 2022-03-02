package com.example.mysql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mysql.adapters.StudentAdapter
import com.example.mysql.databinding.ActivityMainBinding
import com.example.mysql.databinding.ActivityStudentBinding
import com.example.mysql.databinding.ItemDialogBinding
import com.example.mysql.db.MyDbHelper
import com.example.mysql.models.Group
import com.example.mysql.models.Students

class StudentActivity : AppCompatActivity() {
    lateinit var binding: ActivityStudentBinding
    lateinit var group: Group
    lateinit var list: ArrayList<Students>
    lateinit var myDbHelper: MyDbHelper
    lateinit var studentAdapter: StudentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        group = intent.getSerializableExtra("keyGroup") as Group

        binding.apply {
            btnSaveStudent.setOnClickListener {
                val students = Students(
                    etNameStudent.text.toString(),
                    etNumberStudent.text.toString(),
                    group
                )
                myDbHelper.addStudent(students)
                Toast.makeText(this@StudentActivity, "save", Toast.LENGTH_SHORT).show()
                loadData()
            }
        }
        loadData()
    }

    private fun loadData() {
        val mList = ArrayList<Students>()
        list = ArrayList()
        myDbHelper = MyDbHelper(this)
        mList.addAll(myDbHelper.showStudent())
        mList.forEach {
            if (it.group?.id == group.id) {
                list.add(it)
            }
        }
        studentAdapter = StudentAdapter(list, object : StudentAdapter.RvClick {
            override fun itemClick(students: Students) {
                val dialog = AlertDialog.Builder(this@StudentActivity).create()
                dialog.setTitle(students.name)
                dialog.setMessage(
                    "id: ${students.id}\n"
                            + "name: ${students.name}\n"
                            + "number: ${students.number}\n"
                            + "group: ${students.group?.name}"
                )
                dialog.show()

            }

            override fun menuClick(imageView: ImageView, students: Students) {
                    val popupMenu = PopupMenu(this@StudentActivity, imageView)
                        popupMenu.inflate(R.menu.popup_menu)

                    popupMenu.setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.edit_menu->{
                                val dialog = AlertDialog.Builder(this@StudentActivity).create()
                                val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                                dialog.setView(itemDialogBinding.root)
                                itemDialogBinding.etNameStudent.setText(students.name)
                                itemDialogBinding.etNumberStudent.setText(students.number)
                                itemDialogBinding.btnSaveStudent.setOnClickListener {
                                    students.name = itemDialogBinding.etNameStudent.text.toString()
                                    students.number = itemDialogBinding.etNumberStudent.text.toString()
                                    myDbHelper.editStudent(students)
                                    dialog.cancel()
                                    loadData()
                                }
                                    dialog.show()
                            }
                            R.id.delete_menu->{
                                myDbHelper.deleteStudent(students)
                                loadData()
                            }
                        }
                        true
                    }

                popupMenu.show()
            }
        })
        binding.rvStudents.adapter = studentAdapter
    }

}