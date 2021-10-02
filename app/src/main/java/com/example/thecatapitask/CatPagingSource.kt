package com.example.thecatapitask

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.thecatapitask.data.Cat
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "Paging Source"

class CatPagingSource(
    private val myBackend: CatService,
    ) : PagingSource<Int, Cat>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> = try {
        val pageNumber = params.key ?: 0

        Log.d(TAG, "page number $pageNumber")

        val response = myBackend.getListOfCats(page = pageNumber)

        Log.d(TAG, "items ${response.size}")

        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = if (response.isNotEmpty()) pageNumber + 1 else null

        LoadResult.Page(
            data = response,
            prevKey = prevKey,
            nextKey = nextKey
        )

    } catch (e: IOException) {
        LoadResult.Error(e)
    } catch (e: HttpException) {
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        Log.d(TAG, "get refresh key")
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.id?.toInt() }

    }
}