
import java.util.*;
import java.io.*;

public class Chatbot{
    private static String filename = "./WARC201709_wid.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()) {
                if(sc.hasNextInt()) {
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else {
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
        int vocab = Collections.max(corpus) + 1;
		int flag = Integer.valueOf(args[0]);
        if(flag == 100){
			int w = Integer.valueOf(args[1]);
            int count = 0;
            for(int i : corpus) {
            	if(i == w) {
            		count += 1;
            	}
            }
            System.out.println(count);
            System.out.println(String.format("%.7f",count/(double)corpus.size()));
        }
        else if(flag == 200) {
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            double r = (double) n1/n2;
            double[] prob = new double[vocab];
            double p = 0;
            for(int k = 0; k < vocab; k += 1) {
            	int c = 0;
            	prob[k] = p;
            	for(int j : corpus) {
            		if(j == k) { c += 1; }
            	}
            	p += (double) c/corpus.size();
            	if(prob[k] < r && r <= p) {
            		System.out.println(k);
            		System.out.println(String.format("%.7f",prob[k]));
            		System.out.println(String.format("%.7f",p));
            	}
            }
        }
        else if(flag == 300) {
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            for(int i = 0; i < corpus.size(); i += 1) {
            	if(corpus.get(i) == h) {
            		words_after_h.add(corpus.get(i+1));
            		if(corpus.get(i+1) == w) {count += 1;}
            	}
            }
            //output 
            System.out.println(count);
            System.out.println(words_after_h.size());
            System.out.println(String.format("%.7f",count/(double)words_after_h.size()));
        }
        else if(flag == 400) {
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            double r = (double) n1/n2;
            int hcount = 0;
            Map<Integer, Integer> wh = new TreeMap<>();
            for(int i = 0; i < corpus.size() - 1; i += 1) {
            	if(corpus.get(i) == h) {
            		hcount += 1;
            		if(wh.get(corpus.get(i+1)) == null) {
            			wh.put(corpus.get(i+1), 1);
            		} else {
            			int wcount = wh.get(corpus.get(i+1));
            			wh.put(corpus.get(i+1), wcount+1);
            		}
            	}
            }
            double[] prob = new double[vocab];
            prob[0] = 0;
            int e = 1;
            for(Map.Entry<Integer, Integer> entry : wh.entrySet()) {
            	prob[e] = (double) entry.getValue()/hcount + prob[e-1];
            	System.out.println(prob[e]);
            	if(r == 0) {
            		System.out.println(entry.getKey());
            		System.out.println(String.format("%.7f",prob[e-1]));
            		System.out.println(String.format("%.7f",prob[e]));
            		break;
            	} if(prob[e-1] < r && r <= prob[e]) {
            		System.out.println(entry.getKey());
            		System.out.println(String.format("%.7f",prob[e-1]));
            		System.out.println(String.format("%.7f",prob[e]));
            	}
            	e += 1;
            }
        }
        else if(flag == 500) {
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            for(int i = 0; i < corpus.size() - 1; i += 1) {
            	if(corpus.get(i) == h1 && corpus.get(i+1) == h2 && i < corpus.size() - 2) {
            		words_after_h1h2.add(corpus.get(i+2));
            		if(corpus.get(i+2) == w) {
            			count += 1;
            		}
            	}
            }
            //output 
            System.out.println(count);
            System.out.println(words_after_h1h2.size());
            if(words_after_h1h2.size() == 0)
                System.out.println("undefined");
            else
                System.out.println(String.format("%.7f",count/(double)words_after_h1h2.size()));
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
            double r = (double) n1/n2;
            int hcount = 0;
            Map<Integer, Integer> wh = new TreeMap<>();
            for(int i = 0; i < corpus.size() - 1; i += 1) {
            	if(corpus.get(i) == h1 && corpus.get(i+1) == h2 && i < corpus.size() - 2) {
            		hcount += 1;
            		if(wh.get(corpus.get(i+2)) == null) {
            			wh.put(corpus.get(i+2), 1);
            		} else {
            			int wcount = wh.get(corpus.get(i+2));
            			wh.put(corpus.get(i+2), wcount+1);
            		}
            	}
            }
            double[] prob = new double[vocab];
            prob[0] = 0;
            int e = 1;
            for(Map.Entry<Integer, Integer> entry : wh.entrySet()) {
            	prob[e] = (double) entry.getValue()/hcount + prob[e-1];
            	if(r == 0) {
            		System.out.println(entry.getKey());
            		System.out.println(String.format("%.7f",prob[e-1]));
            		System.out.println(String.format("%.7f",prob[e]));
            		break;
            	}
            	if(prob[e-1] < r && r <= prob[e]) {
            		System.out.println(entry.getKey());
            		System.out.println(String.format("%.7f",prob[e-1]));
            		System.out.println(String.format("%.7f",prob[e]));
            	}
            	e += 1;
            }
        }
        else if(flag == 700) {
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1 = 0, h2 = 0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(t == 0) {
                double r = rng.nextDouble();
                double[] prob = new double[vocab];
                double p = 0;
                for(int k = 0; k < vocab; k += 1) {
                	int c = 0;
                	prob[k] = p;
                	for(int j : corpus) {
                		if(j == k) { c += 1; }
                	}
                	p += (double) c/corpus.size();
                	if(prob[k] < r && r <= p) {
                		h1 = k;
                	}
                }
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12) {
                    return;
                }
                r = rng.nextDouble();
                int hcount = 0;
                Map<Integer, Integer> wh = new TreeMap<>();
                for(int i = 0; i < corpus.size() - 1; i += 1) {
                	if(corpus.get(i) == h1) {
                		hcount += 1;
                		if(wh.get(corpus.get(i+1)) == null) {
                			wh.put(corpus.get(i+1), 1);
                		} else {
                			int wcount = wh.get(corpus.get(i+1));
                			wh.put(corpus.get(i+1), wcount+1);
                		}
                	}
                }
                double[] prob2 = new double[vocab];
                prob2[0] = 0;
                int e = 1;
                for(Map.Entry<Integer, Integer> entry : wh.entrySet()) {
                	prob2[e] = (double) entry.getValue()/hcount + prob2[e-1];
                	if(prob2[e-1] < r && r <= prob2[e]) {
                		h2 = e;
                	}
                	e += 1;
                }
                System.out.println(h2);
            }
            else if(t == 1) {
                h1 = Integer.valueOf(args[3]);
                double r = rng.nextDouble();
                int hcount = 0;
                Map<Integer, Integer> wh = new TreeMap<>();
                for(int i = 0; i < corpus.size() - 1; i += 1) {
                	if(corpus.get(i) == h1) {
                		hcount += 1;
                		if(wh.get(corpus.get(i+1)) == null) {
                			wh.put(corpus.get(i+1), 1);
                		} else {
                			int wcount = wh.get(corpus.get(i+1));
                			wh.put(corpus.get(i+1), wcount+1);
                		}
                	}
                }
                double[] prob = new double[vocab];
                prob[0] = 0;
                int e = 1;
                for(Map.Entry<Integer, Integer> entry : wh.entrySet()) {
                	prob[e] = (double) entry.getValue()/hcount + prob[e-1];
                	if(prob[e-1] < r && r <= prob[e]) {
                		h2 = e;
                	}
                	e += 1;
                }
                System.out.println(h2);
            }
            else if(t == 2) {
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }
            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = 0;
                int hcount = 0;
                Map<Integer, Integer> wh = new TreeMap<>();
                for(int i = 0; i < corpus.size() - 1; i += 1) {
                	if(corpus.get(i) == h1 && corpus.get(i+1) == h2 && i < corpus.size() - 2) {
                		hcount += 1;
                		if(wh.get(corpus.get(i+2)) == null) {
                			wh.put(corpus.get(i+2), 1);
                		} else {
                			int wcount = wh.get(corpus.get(i+2));
                			wh.put(corpus.get(i+2), wcount+1);
                		}
                	}
                }
                double[] prob = new double[vocab];
                prob[0] = 0;
                int e = 1;
                for(Map.Entry<Integer, Integer> entry : wh.entrySet()) {
                	prob[e] = (double) entry.getValue()/hcount + prob[e-1];
                	if(prob[e-1] < r && r <= prob[e]) {
                		w = e;
                	}
                	e += 1;
                }
                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }

        return;
    }
}
