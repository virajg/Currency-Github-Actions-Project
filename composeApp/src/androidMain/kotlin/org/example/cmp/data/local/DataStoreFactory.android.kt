package org.example.cmp.data.local

import android.content.Context
import org.example.cmp.data.local.cache.DATA_STORE_NAME
import org.example.cmp.data.local.cache.createDataStore


fun dataStoreProvider(context: Context) = createDataStore(
    producePath = { context.filesDir.resolve(DATA_STORE_NAME).absolutePath }
)