import java.util.*;

public class successor {
    public static class JugState {
        int[] Capacity = new int[]{0,0};
        int[] Content = new int[]{0,0};
        public JugState(JugState copyFrom)
        {
            this.Capacity[0] = copyFrom.Capacity[0];
            this.Capacity[1] = copyFrom.Capacity[1];
            this.Content[0] = copyFrom.Content[0];
            this.Content[1] = copyFrom.Content[1];
        }
        public JugState()
        {
        }
        public JugState(int A,int B)
        {
            this.Capacity[0] = A;
            this.Capacity[1] = B;
        }
        public JugState(int A,int B, int a, int b)
        {
            this.Capacity[0] = A;
            this.Capacity[1] = B;
            this.Content[0] = a;
            this.Content[1] = b;
        }
 
        public void printContent()
        {
            System.out.println(this.Content[0] + " " + this.Content[1]);
        }
 
        public ArrayList<JugState> getNextStates(){
            ArrayList<JugState> successors = new ArrayList<>();

            //Jug A is emptied
            if (this.Content[0]>0) {
            	JugState dumpA = new JugState(this.Capacity[0],this.Capacity[1],0,this.Content[1]);
            	successors.add(dumpA);
            }
            //Jug A is poured into Jug B
            if (this.Content[0]>0 && this.Content[1] != this.Capacity[1]) {
            	if (this.Capacity[1]-this.Content[1] >= this.Capacity[0]) {
                	JugState AintoB = new JugState(this.Capacity[0],this.Capacity[1],0,this.Content[0]+this.Content[1]);
                	successors.add(AintoB);
            	} else {
            		JugState AintoB = new JugState(this.Capacity[0],this.Capacity[1],this.Content[0]-(this.Capacity[1]-this.Content[1]),this.Capacity[1]);
            		successors.add(AintoB);
            	}
            }
            //Jug B is emptied
            if (this.Content[1]>0) {
            	JugState dumpB = new JugState(this.Capacity[0],this.Capacity[1],this.Content[0],0);
            	successors.add(dumpB);
            }
            //Jug B is filled
            if (this.Content[1] != this.Capacity[1]) {
            	JugState fillB = new JugState(this.Capacity[0],this.Capacity[1],this.Content[0],this.Capacity[1]);
            	successors.add(fillB);
            }
            //Jug B is poured into Jug A
            if (this.Content[1]>0 && this.Content[0] != this.Capacity[0]) {
            	if (this.Capacity[0]-this.Content[0] >= this.Content[1]) {
            		JugState BintoA = new JugState(this.Capacity[0],this.Capacity[1],this.Content[0]+this.Content[1],0);
            		successors.add(BintoA);
            	} else {
            		JugState BintoA = new JugState(this.Capacity[0],this.Capacity[1],this.Capacity[0],this.Content[1]-(this.Capacity[0]-this.Content[0]));
            		successors.add(BintoA);
            	}
            }
            //Jug A is filled
            if (this.Content[0] != this.Capacity[0]) {
            	JugState fillA = new JugState(this.Capacity[0],this.Capacity[1],this.Capacity[0],this.Content[1]);
            	successors.add(fillA);
            }
            // Use this snippet to sort the successors
            Collections.sort(successors, (s1, s2) -> {
                if (s1.Content[0] < s2.Content[0]) {
                    return -1;
                } else if (s1.Content[0] > s2.Content[0]) {
                    return 1;
                } else return Integer.compare(s1.Content[1], s2.Content[1]);
            });
            return successors;
        }
    }

    public static void main(String[] args) {
        if( args.length != 4 )
        {
            System.out.println("Usage: java successor [A] [B] [a] [b]");
            return;
        }

        // parse command line arguments
        JugState a = new JugState();
        a.Capacity[0] = Integer.parseInt(args[0]);
        a.Capacity[1] = Integer.parseInt(args[1]);
        a.Content[0] = Integer.parseInt(args[2]);
        a.Content[1] = Integer.parseInt(args[3]);

        // Implement this function
        ArrayList<JugState> asist = a.getNextStates();

        // Print out generated successors
        for(int i=0;i< asist.size(); i++)
        {
            asist.get(i).printContent();
        }
        return;
    }
}