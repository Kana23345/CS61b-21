package timingtest;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns=new AList<>();
        AList<Double> times=new AList<>();
        AList<Integer> opCounts=new AList<>();
        int count;
        int M=10000;
        Stopwatch st=new Stopwatch();
        for (int i=0;i<12;i++)
        {
            count= (int) (Math.pow(2,i)*1000);
            Ns.addLast(count);
            opCounts.addLast(M);
            AList <Integer> al=new AList<>();
            for(int j=0;j<count-M;j++)
            {
                al.addLast(j);
            }
            st.elapsedTime();
            for (int k=0;k<M;k++)
            {
                al.addLast(k);
            }
            times.addLast(st.elapsedTime());

        }
        printTimingTable(Ns,times,opCounts);
    }

}
