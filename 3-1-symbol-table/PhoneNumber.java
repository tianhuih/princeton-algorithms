import java.util.HashSet;

/**
 * implements a US phone number class with equal() method
 */
public class PhoneNumber {

    private final int area;    // area code (3 digits)
    private final int prefix;  // prefix (3 digits)
    private final int ext;     // extension (4 digits)

    public PhoneNumber(int area, int prefix, int ext) {
        this.area = area;
        this.prefix = prefix;
        this.ext = ext;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        PhoneNumber o = (PhoneNumber) other;
        return this.area == o.area &&
                this.prefix == o.prefix &&
                this.ext == o.ext;
    }

    @Override
    public String toString() {
        return String.format("(%03d) %03d-%04d",area, prefix, ext);
    }

    @Override
    public int hashCode() {
        return 41 * (area + 41 * prefix) + ext;
    }

    public static void main(String[] args) {
        PhoneNumber a = new PhoneNumber(609, 258, 4455);
        PhoneNumber b = new PhoneNumber(609, 876, 5309);
        PhoneNumber c = new PhoneNumber(609, 555, 5309);
        PhoneNumber d = new PhoneNumber(215, 876, 5309);
        PhoneNumber e = new PhoneNumber(609, 876, 5309);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
        System.out.println("e = " + e);

        HashSet<PhoneNumber> set = new HashSet<PhoneNumber>();
        set.add(a);
        set.add(b);
        set.add(c);
        System.out.println("Added a, b, and c");
        System.out.println("contains a:  " + set.contains(a));
        System.out.println("contains b:  " + set.contains(b));
        System.out.println("contains c:  " + set.contains(c));
        System.out.println("contains d:  " + set.contains(d));
        System.out.println("contains e:  " + set.contains(e));
        System.out.println("b == e:      " + (b == e));
        System.out.println("b.equals(e): " + (b.equals(e)));
    }
}
