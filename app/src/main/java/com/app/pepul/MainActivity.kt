package com.app.pepul

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.pepul.databinding.ActivityMainBinding
import com.app.pepul.databinding.ListItemBinding
import com.app.pepul.model.DataItem
import com.app.pepul.model.GetRequest
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    val appViewModel: AppViewModel by viewModels {
        AppViewModel.AppModelFactory((application as IVApplication).appRepository)
    }
    val lastItemId = ""
    lateinit var binding: ActivityMainBinding
    val progressDialog = ProgressDialog(this)
    private val listItem by lazy { ArrayList<DataItem>() }
    lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading Please wait")
        adapter = ListAdapter(this, listItem)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(false)
        binding.recyclerview.adapter = adapter

        appViewModel.loading.observe(this) {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }

        appViewModel.getList.observe(this) {
            if (it.statusCode == 200) {
                listItem.clear()
                listItem.addAll(it.data)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "no list found", Toast.LENGTH_SHORT).show()
            }
        }

        appViewModel.errorMessage.observe(this) {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        }

        appViewModel.getAll(GetRequest(lastFetchId = lastItemId))

    }

    class ListAdapter(private val context: Context, private val listItem: List<DataItem>) :
        RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

        class ListViewHolder(itemBinding: ListItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {
            val itemBinding: ListItemBinding = itemBinding
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val itemBinding: ListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
            return ListViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            Glide.with(context).load(listItem.get(position).file).into(holder.itemBinding.image)
        }

        override fun getItemCount(): Int {
            return listItem.size
        }
    }
}