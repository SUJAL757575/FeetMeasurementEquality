// 🧠 Standalone Enum (Weight Units, base = Kilogram)
enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),          // 1 g = 0.001 kg
    POUND(0.453592);      // 1 lb ≈ 0.453592 kg

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    // Convert to base unit (kg)
    public double toBase(double value) {
        return value * toKgFactor;
    }

    // Convert from base unit (kg)
    public double fromBase(double baseValue) {
        return baseValue / toKgFactor;
    }
}

// 🧠 Main Class
public class FeetMeasurementEquality {

    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // 🔄 Convert weight
        public QuantityWeight convertTo(WeightUnit target) {
            double base = unit.toBase(value);
            double result = target.fromBase(base);
            return new QuantityWeight(result, target);
        }

        // ➕ Add weights (with target unit)
        public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sum = this.unit.toBase(this.value) + other.unit.toBase(other.value);
            double result = targetUnit.fromBase(sum);

            return new QuantityWeight(result, targetUnit);
        }

        // ✅ Equality check
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;

            return Double.compare(
                    this.unit.toBase(this.value),
                    other.unit.toBase(other.value)
            ) == 0;
        }

        @Override
        public String toString() {
            return "QuantityWeight(" + value + ", " + unit + ")";
        }
    }

    // 🚀 Main Method (Demo)
    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        // Equality
        System.out.println("1 kg == 1000 g → " + w1.equals(w2));

        // Conversion
        System.out.println("1 kg → pound → " + w1.convertTo(WeightUnit.POUND));

        // Addition
        QuantityWeight w3 = new QuantityWeight(2.0, WeightUnit.KILOGRAM);
        QuantityWeight w4 = new QuantityWeight(500.0, WeightUnit.GRAM);

        System.out.println("2 kg + 500 g → " + w3.add(w4, WeightUnit.KILOGRAM));
    }
}