package org.example.cmp.data.local

import kotlinx.cinterop.ExperimentalForeignApi
import org.example.cmp.data.local.cache.DATA_STORE_NAME
import org.example.cmp.data.local.cache.createDataStore
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun dataStoreProvider() = createDataStore {
    val directory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
        )
    requireNotNull(directory).path + "/$DATA_STORE_NAME"
}