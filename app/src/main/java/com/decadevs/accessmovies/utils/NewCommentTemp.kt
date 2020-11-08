//package com.decadevs.accessmovies.utils
//
//import com.decadevs.accessmovies.data.Comment
//import com.decadevs.accessmovies.viewmodel.MovieViewModel
//import com.google.firebase.database.FirebaseDatabase
//
//class NewCommentTemp() {
//    lateinit var movieViewModel: MovieViewModel
//    val database = FirebaseDatabase.getInstance().reference
//
//
//    movieViewModel = ViewmodelProvider(this).get(NewMovieViewModel::class.java)
//
//    fun addNewComment() {
//        /** SEND COMMENT DATA TO FIREBASE */
//        val comment = Comment(id, movieId, user, comment)
//        movieViewModel.addNewComment(comment)
//    }
//
//}