// 🧠 Standalone Enum (handles conversion)
enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.0328084);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    // Convert to base unit (feet)
    public double toBase(double value) {
        return value * toFeetFactor;
    }

    // Convert from base unit (feet)
    public double fromBase(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

// 🧠 Main Class
public class FeetMeasurementEquality {

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

        // 🔄 Convert
        public Quantity convertTo(LengthUnit target) {
            double base = unit.toBase(value);
            double result = target.fromBase(base);
            return new Quantity(result, target);
        }

        // ➕ Add with target unit
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sum = this.unit.toBase(this.value) + other.unit.toBase(other.value);
            double result = targetUnit.fromBase(sum);

            return new Quantity(result, targetUnit);
        }

        // ✅ Equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(
                    this.unit.toBase(this.value),
                    other.unit.toBase(other.value)
            ) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // 🚀 Main Method (Demo)
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        // Equality
        System.out.println("Equal: " + q1.equals(q2));

        // Conversion
        System.out.println("Convert: " + q1.convertTo(LengthUnit.INCH));

        // Addition
        System.out.println("Add (FEET): " + q1.add(q2, LengthUnit.FEET));
        System.out.println("Add (YARD): " + q1.add(q2, LengthUnit.YARD));
    }
}