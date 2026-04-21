public class FeetMeasurementEquality {

    // 🧠 Enum (base = feet)
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

        // 🔄 Convert to base
        private double toFeet() {
            return unit.toFeet(value);
        }

        // ➕ Add two quantities
        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            double sumInFeet = this.toFeet() + other.toFeet();   // Step 1: normalize
            double resultValue = this.unit.fromFeet(sumInFeet);  // Step 2: convert to own unit

            return new Quantity(resultValue, this.unit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // 🚀 Main Method
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        Quantity result1 = q1.add(q2);
        System.out.println("1 ft + 12 inch = " + result1);

        Quantity q3 = new Quantity(12.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.FEET);

        Quantity result2 = q3.add(q4);
        System.out.println("12 inch + 1 ft = " + result2);

        Quantity q5 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q6 = new Quantity(3.0, LengthUnit.FEET);

        System.out.println("1 yard + 3 ft = " + q5.add(q6));
    }
}