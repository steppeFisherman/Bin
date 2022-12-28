package com.example.bin.data.model.mappers

import com.example.bin.data.model.DataCache
import com.example.bin.data.model.DataCloud
import com.example.bin.utils.FormatCountryName
import com.example.bin.utils.FormatPhoneNumber

interface MapCloudToCache {

    fun transform(dataCloud: DataCloud, binNumber: String): DataCache

    class Base(
        private val phoneFormat: FormatPhoneNumber,
        private val countryNameFormat: FormatCountryName
    ) : MapCloudToCache {
        override fun transform(dataCloud: DataCloud, binNumber: String): DataCache {

            var phoneCloud: String = dataCloud.bank?.phone ?: ""
            if (!phoneCloud.startsWith("+")) {
                phoneCloud = phoneFormat.modify(phoneCloud)
            }

            val countryAlpha: String = dataCloud.country?.alpha2 ?: ""
            val countryName: String = dataCloud.country?.name ?: ""
            val countryNameModified = countryNameFormat.modify(countryAlpha, countryName)

            return DataCache(
                id = 0,
                binNumber = binNumber,
                brand = dataCloud.brand ?: "",
                prepaid = dataCloud.prepaid ?: false,
                scheme = dataCloud.scheme ?: "",
                type = dataCloud.type ?: "",

                city = dataCloud.bank?.city ?: "",
                nameBank = dataCloud.bank?.name ?: "",
                phone = phoneCloud,
                url = dataCloud.bank?.url ?: "",

                alpha2 = dataCloud.country?.alpha2 ?: "",
                currency = dataCloud.country?.currency ?: "",
                emoji = dataCloud.country?.emoji ?: "",
                latitude = dataCloud.country?.latitude ?: 0,
                longitude = dataCloud.country?.longitude ?: 0,
                nameCountry = countryNameModified,
                numeric = dataCloud.country?.numeric ?: "",

                length = dataCloud.number?.length ?: 0,
                luhn = dataCloud.number?.luhn ?: false
            )
        }
    }
}


