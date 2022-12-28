package com.example.bin.ui.model

import com.example.bin.domain.model.DataDomain

interface MapDomainToUi {

    fun mapDomainToUi(dataDomain: DataDomain): DataUi

    class Base : MapDomainToUi {
        override fun mapDomainToUi(dataDomain: DataDomain): DataUi =
            DataUi(
                dataDomain.id,
                dataDomain.binNumber,
                dataDomain.brand,
                dataDomain.prepaid,
                dataDomain.scheme,
                dataDomain.type,

                dataDomain.city,
                dataDomain.nameBank,
                dataDomain.phone,
                dataDomain.url,

                dataDomain.alpha2,
                dataDomain.currency,
                dataDomain.emoji,
                dataDomain.latitude,
                dataDomain.longitude,
                dataDomain.nameCountry,
                dataDomain.numeric,

                dataDomain.length,
                dataDomain.luhn
            )
    }
}