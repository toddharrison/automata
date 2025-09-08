//package com.eharrison.automata;
//
//import lombok.val;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//public class SignalProcessorTest {
//    private SignalProcessor processor;
//
//    @BeforeEach
//    public void setUp() {
//        processor = new SignalProcessor();
//    }
//
//    @Nested
//    public class GetDetectedSignalTest {
//        @Test
//        public void call() {
//            // Arrange
//            val source = new Location(0, 0);
//            val target = new Location(1, 0);
//            val signalSource = SignalEmission.builder()
//                    .source(source)
//                    .target(target)
////                    .arcLength(1.0)
//                    .amplitude(100.0)
//                    .frequency(10)
//                    .build();
//
//            // Act
//            val result = processor.getDetectedSignal(signalSource);
//
//            // Assert
//            System.out.println(result);
//        }
//    }
//
//    @Nested
//    public class GetReflectedSignalTest {
//        @Test
//        public void call() {
//            // Arrange
//            val source = new Location(0, 0);
//            val target = new Location(1, 0);
//            val signalSource = SignalEmission.builder()
//                    .source(source)
//                    .target(target)
////                    .arcLength(1.0)
//                    .amplitude(100.0)
//                    .frequency(10)
//                    .build();
//
//            // Act
//            val result = processor.getReflectedSignal(signalSource);
//
//            // Assert
//            System.out.println(result);
//        }
//    }
//}
