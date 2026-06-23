package com.example.companytime.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.companytime.data.dto.PersonDto

class PersonPagingDataSource(
    private val apiService: ApiService
): PagingSource<Int, PersonDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonDto> {
        return try {
            val page = params.key ?: 0
            val response = apiService.getPaginatedPersons(page = page, size = params.loadSize)

            LoadResult.Page(
                data = response.content,
                nextKey = if (response.last || response.content.isEmpty()) null else page + 1,
                prevKey = if (page == 0) null else page - 1
            )
        } catch(e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PersonDto>): Int? {
        return state.anchorPosition
    }
}