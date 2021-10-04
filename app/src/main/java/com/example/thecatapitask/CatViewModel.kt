package com.example.thecatapitask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.thecatapitask.data.Cat
import kotlinx.coroutines.flow.Flow

class CatViewModel : ViewModel() {

    lateinit var retroService: CatService

    init {
        retroService = RetroInstance.getRetroInstance().create(CatService::class.java)
    }

    val catList: Flow<PagingData<Cat>> = Pager( config = PagingConfig(
            pageSize = CatService.DEFAULT_PAGE_SIZE,
            enablePlaceholders = true,
            maxSize = 100 ),
            pagingSourceFactory = { CatPagingSource(retroService) }
        ).flow.cachedIn(viewModelScope)

}
