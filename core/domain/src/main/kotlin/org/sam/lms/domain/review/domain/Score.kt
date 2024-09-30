package org.sam.lms.domain.review.domain

class Score {
    private var _value: Int = 0

    val value get() = _value

    companion object {
        fun create(value: Int): Score {
            val score = Score()
            if (value < 0 || value > 5) {
                throw IllegalArgumentException("점수는 0점에서 5점 사이입니다.")
            }
            score._value = value
            return score
        }
    }
}