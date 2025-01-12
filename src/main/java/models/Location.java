 package models;

public enum Location {
    A {
        public double travelTime(Location to){
            return travelTime(A,to);
        }

    },
    B{
        public double travelTime(Location to) {
            return travelTime(B,to);
        }

    },
    C{
        public double travelTime(Location to) {
            return travelTime(C,to);
        }

    };

    public abstract double travelTime(Location to);

    static double travelTime(Location from, Location to) {
        if(ab(from,to))
        {
            return 2.0;
        } else if(ac(from,to))
        {
            return 4.0;
        } else if(bc(from,to))
        {
            return 3.0;
        }
        return 0.0; //same location
    }

    static boolean ab(Location from, Location to)
    {
        return (from == A && to == B) || (from == B && to == A);
    }

    static  boolean ac(Location from, Location to)
    {
        return (from == A && to == C) || (from == C && to == A);
    }

    static boolean bc(Location from, Location to)
    {
        return (from == B && to == C) || (from == C && to == B);
    }

}
