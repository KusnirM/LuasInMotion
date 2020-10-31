package com.example.luasinmotionandroid.domain.model

import com.example.luasinmotionandroid.data.model.Stop

enum class DatePart(val direction: Direction.Name, val stop: Stop) {
    EVENING(Direction.Name.INBOUND, Stop.STILLORGAN),
    MORNING(Direction.Name.OUTBOUND, Stop.MARLBOROUGH)
}
