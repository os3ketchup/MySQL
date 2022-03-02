package com.example.mysql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mysql.adapters.GroupAdapter
import com.example.mysql.databinding.ActivityMainBinding
import com.example.mysql.db.MyDbHelper
import com.example.mysql.models.Group

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var groupAdapter: GroupAdapter
    lateinit var list: ArrayList<Group>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        myDbHelper = MyDbHelper(this)
        update()

        binding.apply {
            btnGroupSave.setOnClickListener {
                val name = etGroup.text.toString().trim()
                val group = Group(name)
                myDbHelper.addGroup(group)
                update()
                Toast.makeText(this@MainActivity, "saved $name", Toast.LENGTH_SHORT).show()

            }
        }

    }


    fun update() {

        list = myDbHelper.showGroup() as ArrayList<Group>
        groupAdapter = GroupAdapter(list,object :GroupAdapter.RvClick{
            override fun itemClick(group: Group) {
                val intent = Intent(this@MainActivity,StudentActivity::class.java)
                intent.putExtra("keyGroup",group)
                startActivity(intent)
            }

            override fun longClick(group: Group) {
                myDbHelper.deleteGroup(group)
                update()
            }
        })
        binding.rvGroups.adapter = groupAdapter
    }
}