package org.sam.lms.store.domain.payment.application.out

import org.sam.lms.store.domain.payment.domain.PaymentStatus

class PaymentResultDto(val status: PaymentStatus, val message: String)