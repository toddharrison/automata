//package com.eharrison.automata;
//
//import lombok.Builder;
//
//import static com.eharrison.automata.Verify.require;
//
//@Builder
//public record SignalEmission(
//        Location source,
//        Location target,
//        double amplitude,
//        double arcLength,
//        int frequency
//) {
//    public static class SignalEmissionBuilder {
//        // Define default values
//        SignalEmissionBuilder() {
//            arcLength = SignalProcessor.TAU;
//        }
//
//        // Validate properties
//        public SignalEmission build() {
//            require(amplitude > 0, "Amplitude out of bounds");
//            require(arcLength > 0 && arcLength <= SignalProcessor.TAU, "Arc length out of bounds");
//
//            return new SignalEmission(source, target, amplitude, arcLength, frequency);
//        }
//    }
//
//    public <T> T decompose(final Properties<T> props) {
//        return props.apply(source, target, amplitude, arcLength, frequency);
//    }
//
//    @FunctionalInterface
//    public interface Properties<T> {
//        T apply(Location source, Location target, double amplitude, double arcLength, int frequency);
//    }
//}
