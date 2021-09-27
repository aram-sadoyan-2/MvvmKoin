package com.camp.mvvmkoin.repo

import android.content.Context
import android.net.ConnectivityManager
import com.camp.mvvmkoin.localdb.*
import com.camp.mvvmkoin.model.Hits
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val api: ApiInterface,
    private val context: Context,
    private val dao: AppDAO
) : Repository() {

    override suspend fun getAllImages(): AppResult<List<Hits>> {
        if (isOnline(context)) {
            return try {
                val response = api.getHitData()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.IO) {
                            dao.insert(it.listofHits)
                        }
                        handleSuccess(it.listofHits)
                    } ?: run {
                        handleApiError("error")
                    }
                } else {
                    handleApiError("error")
                }
            } catch (e: Exception) {
                AppResult.Error(e)
            }

        } else {
            val data = getImagesDataFromCache()
            return if (data.isNotEmpty()) {
                AppResult.Success(data)
            } else {
                handleApiError("error from Room")
            }

        }

    }

    private suspend fun getImagesDataFromCache(): List<Hits> {
        return withContext(Dispatchers.IO) {
            dao.getAll()
        }
    }


    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}