public class FeetMeasurementEquality {

    // 🧠 Enum (base unit = feet)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeet;
        }
    }

    // 🧠 Quantity Class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // 🔄 Convert to another unit
        public double convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double feetValue = unit.toFeet(value);      // Step 1: to base (feet)
            return targetUnit.fromFeet(feetValue);      // Step 2: to target
        }

        // Override equals (same as UC4)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double thisFeet = unit.toFeet(value);
            double otherFeet = other.unit.toFeet(other.value);

            return Double.compare(thisFeet, otherFeet) == 0;
        }
    }

    // 🔵 Static convert API (IMPORTANT)
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        Quantity q = new Quantity(value, source);
        return q.convertTo(target);
    }

    // 🚀 Main Method
    public static void main(String[] args) {

        System.out.println("1 ft → inches = " + convert(1.0, LengthUnit.FEET, LengthUnit.INCH));
        System.out.println("3 yard → feet = " + convert(3.0, LengthUnit.YARD, LengthUnit.FEET));
        System.out.println("36 inch → yard = " + convert(36.0, LengthUnit.INCH, LengthUnit.YARD));
        System.out.println("1 cm → inch = " + convert(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH));
    }
}