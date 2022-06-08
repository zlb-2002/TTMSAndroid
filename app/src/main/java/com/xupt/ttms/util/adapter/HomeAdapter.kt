package com.xupt.ttms.util.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xupt.ttms.R
import com.xupt.ttms.data.bean.movieBean.DataSource
import com.xupt.ttms.databinding.MovieItemBinding
import com.xupt.ttms.ui.home.InformationActivity
import com.xupt.ttms.ui.home.ScheduleActivity
import com.xupt.ttms.util.tool.ListUtil.myString

class HomeAdapter(private val list: MutableList<DataSource>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.name
        val actor = binding.actor
        val type = binding.movieType
        val area = binding.movieArea
        val language = binding.movieLanguage
        val len = binding.movieLen
        val image = binding.movieImage
        val button = binding.button
        val view = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataSource = list[position]
        holder.image.load(dataSource.cover){
            error(R.drawable.ic_home_black_24dp)
        }
        holder.title.text = dataSource.title
        if (dataSource.actor?.size!! > 3) {
            holder.actor.text = "${dataSource.actor[0]},${dataSource.actor[1]},${dataSource.actor[2]}"
        } else {
            holder.actor.text = dataSource.actor.myString()
        }
        holder.type.text = dataSource.type.toString()
        holder.area.text = dataSource.area.toString()
        holder.language.text = dataSource.language.toString()
        holder.len.text = "${dataSource.filmlen}分钟"
        holder.button.apply {
            setOnClickListener {
                context.startActivity(Intent(context, ScheduleActivity::class.java).putExtra("movieId", dataSource.mid.toString()))
            }
        }

        holder.view.apply {
            setOnClickListener {
                context.startActivity(Intent(context, InformationActivity::class.java).putExtra("dataSource", dataSource))
            }
        }
    }

    override fun getItemCount(): Int = list.size
}