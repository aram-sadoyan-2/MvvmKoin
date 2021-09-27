package com.camp.mvvmkoin.repo

import com.camp.mvvmkoin.localdb.AppResult
import com.camp.mvvmkoin.model.Hits

abstract class Repository {
    abstract suspend fun getAllImages() : AppResult<List<Hits>>
}