package com.mad.it19073774.Database;

import android.provider.BaseColumns;

public final class DatabaseMaster {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DatabaseMaster() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String USERNAME = "userName";
        public static final String PASSWORD = "password";
        public static final String USERTYPE = "userType";
    }

    public static class Movie implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String MOVIENAME = "movieName";
        public static final String MOVIEYEAR = "movieYear";
    }

    public static class Comments implements BaseColumns {
        public static final String TABLE_NAME = "comments";
        public static final String MOVIENAME = "movieName";
        public static final String RATING = "movieRating";
        public static final String COMMENTS = "comments";
    }
}