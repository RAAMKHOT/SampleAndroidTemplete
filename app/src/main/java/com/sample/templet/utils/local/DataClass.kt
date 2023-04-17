package com.sample.templet.utils.local

class DataClass {
    private var userId: String? =null
    private var authToken: String? =null
    private var schemaName: String? = null

    companion object {
        private var dataClass: DataClass? = null
        @Synchronized
        fun getInstance(): DataClass? {
            if (dataClass == null) {
                dataClass = DataClass()
            }
            return dataClass
        }
    }

    fun getUserId(): String? {
        return if (userId != null) userId else ""
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    fun getSchemaName(): String? {
        return schemaName
    }

    fun setSchemaName(schemaName: String?) {
        this.schemaName = schemaName
    }

    fun getAuthToken(): String? {
        return authToken
    }

    fun setAuthToken(authToken: String?) {
        this.authToken = authToken
    }

}