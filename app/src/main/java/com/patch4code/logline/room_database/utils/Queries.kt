package com.patch4code.logline.room_database.utils

object Queries {

    const val COUNT_MOVIE_REFERENCES = """
        SELECT 
            (SELECT COUNT(*) FROM MovieUserData WHERE movieId = :movieId) +
            (SELECT COUNT(*) FROM LoggedMovie WHERE movieId = :movieId) +
            (SELECT COUNT(*) FROM MovieInList WHERE movieId = :movieId) +
            (SELECT COUNT(*) FROM UserProfile WHERE favouriteMovieId1 = :movieId) +
            (SELECT COUNT(*) FROM UserProfile WHERE favouriteMovieId2 = :movieId) +
            (SELECT COUNT(*) FROM UserProfile WHERE favouriteMovieId3 = :movieId) +
            (SELECT COUNT(*) FROM UserProfile WHERE favouriteMovieId4 = :movieId)
    """
}