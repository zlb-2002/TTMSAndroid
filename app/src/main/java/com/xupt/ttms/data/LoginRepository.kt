package com.xupt.ttms.data

import com.xupt.ttms.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }



    suspend fun login(username: String, password: String): Boolean {
        return dataSource.login(username, password)
    }

    suspend fun getCode(phone: String): Boolean {
        return dataSource.getCode(phone)
    }


}