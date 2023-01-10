package com.payments.services;

import com.payments.models.Payment;
import com.payments.models.PaymentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class PaymentServiceTest {

    @ParameterizedTest
    @MethodSource("generateData")
    void testPaymentsSortByDate(List<Payment> paymentsInput,List<Payment> paymentsExpected ) {
        Assertions.assertEquals(paymentsExpected,PaymentService.sortByDate(paymentsInput));
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                new Payment(100,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.PREPARED, LocalDateTime.of(2023, 8,12,15,30)),
                                new Payment(200,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.SENT, LocalDateTime.of(2022, 8,13,15,30)),
                                new Payment(200,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.SENT, LocalDateTime.of(2021, 8,13,15,30)),
                                new Payment(200,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.PREPARED, LocalDateTime.of(2021, 8,13,15,29))
                        ),
                        Arrays.asList(
                                new Payment(200,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.PREPARED, LocalDateTime.of(2021, 8,13,15,29)),
                                new Payment(200,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.SENT, LocalDateTime.of(2021, 8,13,15,30)),
                                new Payment(200,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.SENT, LocalDateTime.of(2022, 8,13,15,30)),
                                new Payment(100,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.PREPARED, LocalDateTime.of(2023, 8,12,15,30))
                        )
                ),
                Arguments.of(
                        Arrays.asList(
                                new Payment(300,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.SENT, LocalDateTime.of(2022, 8,13,15,30)),
                                new Payment(200,3, "22222222222222", "22222222222222","Recipient", PaymentStatus.PREPARED, LocalDateTime.of(2022, 8,13,15,30)),
                                new Payment(500,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.SENT, LocalDateTime.of(2022, 8,13,15,30))
),
                        Arrays.asList(
                                new Payment(300,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.SENT, LocalDateTime.of(2022, 8,13,15,30)),
                                new Payment(200,3, "22222222222222", "22222222222222","Recipient", PaymentStatus.PREPARED, LocalDateTime.of(2022, 8,13,15,30)),
                                new Payment(500,4, "11111111111111", "22222222222222","Recipient", PaymentStatus.SENT, LocalDateTime.of(2022, 8,13,15,30))
)
                )
            //    Arguments.of(Arrays.asList("x", "y", "z"))
        );
    }
}