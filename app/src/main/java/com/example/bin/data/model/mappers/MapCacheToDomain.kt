package com.example.bin.data.model.mappers

import com.example.bin.data.model.DataCache
import com.example.bin.domain.model.DataDomain

interface MapCacheToDomain {

    fun transform(dataCache: DataCache): DataDomain

    class Base : MapCacheToDomain {
        override fun transform(dataCache: DataCache): DataDomain =
            DataDomain(
                dataCache.id,
                dataCache.binNumber,
                dataCache.brand,
                dataCache.prepaid,
                dataCache.scheme,
                dataCache.type,

                dataCache.city,
                dataCache.nameBank,
                dataCache.phone,
                dataCache.url,

                dataCache.alpha2,
                dataCache.currency,
                dataCache.emoji,
                dataCache.latitude,
                dataCache.longitude,
                dataCache.nameCountry,
                dataCache.numeric,

                dataCache.length,
                dataCache.luhn
            )
    }
}