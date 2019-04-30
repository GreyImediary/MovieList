package com.example.movielab.ui.adapters

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielab.R
import com.example.movielab.inflate
import com.example.movielab.models.Movie
import com.example.movielab.movieIdKey
import com.example.movielab.posterDefaultPath
import com.example.movielab.ui.activities.DetailedMovieActivity
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieApater : ListAdapter<Movie, MovieApater.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.item_movie))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {

            if (movie.posterPath != null) {
                val posterPath = posterDefaultPath + movie.posterPath

                Glide.with(itemView)
                        .load(posterPath)
                        .centerCrop()
                        .into(itemView.moviePoster)

            }

            itemView.title.text = movie.title
            itemView.originalTitle.text = movie.originalTitle
            itemView.rating.text = movie.voteAverage.toString()
            itemView.releaseDate.text = movie.releaseDate
            itemView.description.text = if (movie.overview.length < 200) movie.overview
                else itemView.resources.getString(R.string.short_desc, movie.overview.substring(0, 200))

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailedMovieActivity::class.java)
                        .apply { putExtra(movieIdKey, movie.id) }

                itemView.context.startActivity(intent)
            }
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem

}