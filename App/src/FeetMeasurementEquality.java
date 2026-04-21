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

        private double toFeet() {
            return unit.toFeet(value);
        }

        // ➕ UC6 (old method - optional)
        public Quantity add(Quantity other) {
            return add(other, this.unit);
        }

        // ➕ UC7 (NEW method with target unit)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double sumFeet = this.toFeet() + other.toFeet();        // Step 1: base
            double result = targetUnit.fromFeet(sumFeet);           // Step 2: convert

            return new Quantity(result, targetUnit);
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

        System.out.println("Result in FEET: " + q1.add(q2, LengthUnit.FEET));
        System.out.println("Result in INCH: " + q1.add(q2, LengthUnit.INCH));
        System.out.println("Result in YARD: " + q1.add(q2, LengthUnit.YARD));

        Quantity q3 = new Quantity(2.54, LengthUnit.CENTIMETER);
        Quantity q4 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("Result in CM: " + q3.add(q4, LengthUnit.CENTIMETER));
    }
}