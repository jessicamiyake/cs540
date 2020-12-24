import java.text.DecimalFormat;

public class Neural {
	public static void main(String args[]) {
		int flag = Integer.parseInt(args[0]);
		double w1 = Double.parseDouble(args[1]);
		double w2 = Double.parseDouble(args[2]);
		double w3 = Double.parseDouble(args[3]);
		double w4 = Double.parseDouble(args[4]);
		double w5 = Double.parseDouble(args[5]);
		double w6 = Double.parseDouble(args[6]);
		double w7 = Double.parseDouble(args[7]);
		double w8 = Double.parseDouble(args[8]);
		double w9 = Double.parseDouble(args[9]);
		double x1, x2, uA, vA, uB, vB, uC, vC, y, error, eta, t;
		double dvAduA, dvBduB, dEdvA, dEduA, dEdvB, dEduB, dEdvC, dEduC, dw1, dw2, dw3, dw4, dw5, dw6, dw7, dw8, dw9;
		
		Double[][] train = {
				{0.98,0.87,1.0},
				{0.92,0.88,0.0},
				{0.9,0.9,1.0},
				{0.95,0.58,0.0},
				{0.98,0.73,1.0},
				{0.46,0.49,0.0},
				{0.97,1.0,1.0},
				{0.97,0.95,1.0},
				{0.9,0.85,0.0},
				{0.62,0.5,0.0},
				{0.98,1.0,1.0},
				{0.77,0.7,0.0},
				{0.94,0.83,1.0},
				{0.95,1.0,1.0},
				{0.84,0.55,0.0},
				{0.79,0.86,1.0},
				{0.91,0.46,0.0},
				{0.97,0.92,1.0},
				{0.91,0.68,0.0},
				{1.0,0.9,1.0},
				{0.77,0.64,0.0},
				{0.97,0.92,1.0},
				{0.77,0.95,1.0},
				{0.97,0.78,0.0},
				{0.7,0.52,0.0},
				{1.0,0.85,1.0},
				{0.8,0.61,0.0},
				{1.0,0.95,1.0},
				{0.96,0.85,1.0},
				{0.93,0.82,1.0},
				{0.97,0.92,1.0},
				{0.97,0.85,1.0},
				{0.83,0.28,0.0},
				{0.9,0.73,1.0},
				{1.0,0.67,0.0},
				{0.93,0.63,0.0},
				{0.86,0.72,0.0},
				{0.52,0.5,0.0},
				{0.78,0.48,0.0},
				{0.33,0.45,0.0},
				{0.94,0.7,1.0},
				{0.87,0.45,0.0},
				{0.97,0.95,1.0},
				{1.0,0.85,0.0},
				{0.83,0.34,0.0},
				{0.77,0.61,0.0},
				{0.92,0.8,0.0},
				{0.9,1.0,1.0},
				{1.0,0.5,0.0},
				{0.95,0.54,0.0},
				{0.98,0.95,1.0},
				{1.0,0.76,1.0},
				{0.73,0.61,0.0},
				{0.67,0.65,0.0},
				{0.49,0.23,0.0},
				{1.0,0.78,0.0},
				{0.63,0.73,0.0},
				{0.44,0.59,0.0},
				{0.85,0.7,0.0},
				{0.83,0.52,0.0},
				{0.45,0.69,0.0},
				{0.82,0.62,0.0},
				{0.88,0.72,0.0},
				{0.97,0.65,0.0},
				{0.73,0.42,0.0},
				{0.81,0.75,0.0},
				{0.94,0.69,0.0},
		};
		Double[][] test = {
				{0.9,0.88,1.0},
				{0.8,0.4,0.0},
				{0.7,0.49,0.0},
				{0.97,0.82,1.0},
				{0.57,0.65,0.0},
				{0.62,0.47,0.0},
				{0.9,0.85,1.0},
				{0.93,0.57,0.0},
				{0.62,0.79,0.0},
				{0.93,0.95,1.0},
				{0.59,0.36,0.0},
				{0.75,0.31,0.0},
				{0.98,0.95,1.0},
				{0.9,0.49,0.0},
				{0.81,0.65,0.0},
				{1.0,0.82,0.0},
				{0.87,0.36,0.0},
				{0.97,0.6,0.0},
				{0.93,0.7,0.0},
				{1.0,0.85,1.0},
				{0.86,0.75,0.0},
				{0.92,0.5,0.0},
				{0.95,0.82,1.0},
				{0.9,0.8,0.0},
				{0.89,0.77,0.0},
		};
		Double[][] eval = {
				{0.72,0.9,1.0},
				{0.83,0.65,0.0},
				{0.91,0.94,1.0},
				{0.89,0.65,0.0},
				{0.83,0.72,0.0},
				{0.76,0.57,0.0},
				{0.9,0.7,0.0},
				{1.0,0.63,0.0},
				{0.6,0.54,0.0},
				{0.89,0.67,0.0},
				{0.74,0.47,0.0},
				{0.86,0.72,1.0},
				{0.83,0.67,0.0},
				{0.77,0.3,0.0},
				{0.97,0.9,1.0},
				{0.72,0.55,0.0},
				{1.0,0.72,1.0},
				{0.93,0.92,1.0},
				{0.87,0.79,0.0},
				{0.46,0.69,0.0},
				{1.0,0.58,0.0},
				{0.75,0.57,0.0},
				{0.87,0.62,0.0},
				{1.0,0.84,1.0},
				{0.92,0.67,0.0},
		};
		
		DecimalFormat df = new DecimalFormat("0.00000");
		switch(flag) {
		case 100:
			x1 = Double.parseDouble(args[10]);
			x2 = Double.parseDouble(args[11]);
			uA = w1 + x1*w2 + x2*w3;
			uB = x1*w5 + x2*w6 + w4;
			vA = Math.max(uA, 0);
			vB = Math.max(uB, 0);
			uC = vA*w8 + vB*w9 + w7;
			vC = 1/(1 + Math.exp(-uC));
			System.out.println(df.format(uA)+" "+df.format(vA)+" "+df.format(uB)+" "+df.format(vB)+" "+df.format(uC)+" "+df.format(vC));
			break;
		case 200:
			x1 = Double.parseDouble(args[10]);
			x2 = Double.parseDouble(args[11]);
			y = Double.parseDouble(args[12]);
			uA = w1 + x1*w2 + x2*w3;
			uB = x1*w5 + x2*w6 + w4;
			vA = Math.max(uA, 0);
			vB = Math.max(uB, 0);
			uC = vA*w8 + vB*w9 + w7;
			vC = 1/(1 + Math.exp(-uC));
			error = Math.pow(vC - y, 2)/2;
			dEdvC =  vC - y;
			dEduC = dEdvC*(vC*(1 - vC));
			System.out.println(df.format(error)+" "+df.format(dEdvC)+" "+df.format(dEduC));
			break;
		case 300:
			x1 = Double.parseDouble(args[10]);
			x2 = Double.parseDouble(args[11]);
			y = Double.parseDouble(args[12]);
			uA = w1 + x1*w2 + x2*w3;
			uB = x1*w5 + x2*w6 + w4;
			vA = Math.max(uA, 0);
			vB = Math.max(uB, 0);
			uC = vA*w8 + vB*w9 + w7;
			vC = 1/(1 + Math.exp(-uC));
			dEdvC =  vC - y;
			dEduC = dEdvC*(vC*(1 - vC));
			if(uA < 0) {dvAduA = 0;} else {dvAduA = 1;}
			dEdvA = w8*dEduC;
			dEduA = dEdvA*dvAduA;
			if(uB < 0) {dvBduB = 0;} else {dvBduB = 1;}
			dEdvB = w9*dEduC;
			dEduB = dEdvB*dvBduB;
			System.out.println(df.format(dEdvA)+" "+df.format(dEduA)+" "+df.format(dEdvB)+" "+df.format(dEduB));
			break;
		case 400:
			x1 = Double.parseDouble(args[10]);
			x2 = Double.parseDouble(args[11]);
			y = Double.parseDouble(args[12]);
			uA = w1 + x1*w2 + x2*w3;
			uB = x1*w5 + x2*w6 + w4;
			vA = Math.max(uA, 0);
			vB = Math.max(uB, 0);
			uC = vA*w8 + vB*w9 + w7;
			vC = 1/(1 + Math.exp(-uC));
			dEdvC =  vC - y;
			dEduC = dEdvC*(vC*(1 - vC));
			if(uA < 0) {dvAduA = 0;} else {dvAduA = 1;}
			dEdvA = w8*dEduC;
			dEduA = dEdvA*dvAduA;
			if(uB < 0) {dvBduB = 0;} else {dvBduB = 1;}
			dEdvB = w9*dEduC;
			dEduB = dEdvB*dvBduB;
			dw1 = dEduA;
			dw2 = x1*dEduA;
			dw3 = x2*dEduA;
			dw4 = dEduB;
			dw5 = x1*dEduB;
			dw6 = x2*dEduB;
			dw7 = dEduC;
			dw8 = vA*dEduC;
			dw9 = vB*dEduC;
			System.out.println(df.format(dw1)+" "+df.format(dw2)+" "+df.format(dw3)+" "+df.format(dw4)+" "+df.format(dw5)+" "+df.format(dw6)+" "+df.format(dw7)+" "+df.format(dw8)+" "+df.format(dw9));
			break;
		case 500:
			x1 = Double.parseDouble(args[10]);
			x2 = Double.parseDouble(args[11]);
			y = Double.parseDouble(args[12]);
			eta = Double.parseDouble(args[13]);
			uA = w1 + x1*w2 + x2*w3;
			uB = x1*w5 + x2*w6 + w4;
			vA = Math.max(uA, 0);
			vB = Math.max(uB, 0);
			uC = vA*w8 + vB*w9 + w7;
			vC = 1/(1 + Math.exp(-uC));
			error = Math.pow(vC - y, 2)/2;
			System.out.println(df.format(w1)+" "+df.format(w2)+" "+df.format(w3)+" "+df.format(w4)+" "+df.format(w5)+" "+df.format(w6)+" "+df.format(w7)+" "+df.format(w8)+" "+df.format(w9));
			System.out.println(df.format(error));
			dEdvC =  vC - y;
			dEduC = dEdvC*(vC*(1 - vC));
			if(uA < 0) {dvAduA = 0;} else {dvAduA = 1;}
			dEdvA = w8*dEduC;
			dEduA = dEdvA*dvAduA;
			if(uB < 0) {dvBduB = 0;} else {dvBduB = 1;}
			dEdvB = w9*dEduC;
			dEduB = dEdvB*dvBduB;
			dw1 = dEduA;
			dw2 = x1*dEduA;
			dw3 = x2*dEduA;
			dw4 = dEduB;
			dw5 = x1*dEduB;
			dw6 = x2*dEduB;
			dw7 = dEduC;
			dw8 = vA*dEduC;
			dw9 = vB*dEduC;
			w1 = w1 - eta*dw1;
			w2 = w2 - eta*dw2;
			w3 = w3 - eta*dw3;
			w4 = w4 - eta*dw4;
			w5 = w5 - eta*dw5;
			w6 = w6 - eta*dw6;
			w7 = w7 - eta*dw7;
			w8 = w8 - eta*dw8;
			w9 = w9 - eta*dw9;
			System.out.println(df.format(w1)+" "+df.format(w2)+" "+df.format(w3)+" "+df.format(w4)+" "+df.format(w5)+" "+df.format(w6)+" "+df.format(w7)+" "+df.format(w8)+" "+df.format(w9));
			uA = w1 + x1*w2 + x2*w3;
			uB = x1*w5 + x2*w6 + w4;
			vA = Math.max(uA, 0);
			vB = Math.max(uB, 0);
			uC = vA*w8 + vB*w9 + w7;
			vC = 1/(1 + Math.exp(-uC));
			error = Math.pow(vC - y, 2)/2;
			System.out.println(df.format(error));
			break;
		case 600:
			eta = Double.parseDouble(args[10]);
			for(int j = 0; j < train.length; j += 1) {
				x1 = train[j][0]; x2 = train[j][1]; y = train[j][2];
				System.out.println(df.format(x1)+" "+df.format(x2)+" "+df.format(y));
				uA = w1 + x1*w2 + x2*w3;
				uB = x1*w5 + x2*w6 + w4;
				vA = Math.max(uA, 0);
				vB = Math.max(uB, 0);
				uC = vA*w8 + vB*w9 + w7;
				vC = 1/(1 + Math.exp(-uC));
				dEdvC =  vC - y;
				dEduC = dEdvC*(vC*(1 - vC));
				if(uA < 0) {dvAduA = 0;} else {dvAduA = 1;}
				dEdvA = w8*dEduC;
				dEduA = dEdvA*dvAduA;
				if(uB < 0) {dvBduB = 0;} else {dvBduB = 1;}
				dEdvB = w9*dEduC;
				dEduB = dEdvB*dvBduB;
				dw1 = dEduA;
				dw2 = x1*dEduA;
				dw3 = x2*dEduA;
				dw4 = dEduB;
				dw5 = x1*dEduB;
				dw6 = x2*dEduB;
				dw7 = dEduC;
				dw8 = vA*dEduC;
				dw9 = vB*dEduC;
				w1 = w1 - eta*dw1;
				w2 = w2 - eta*dw2;
				w3 = w3 - eta*dw3;
				w4 = w4 - eta*dw4;
				w5 = w5 - eta*dw5;
				w6 = w6 - eta*dw6;
				w7 = w7 - eta*dw7;
				w8 = w8 - eta*dw8;
				w9 = w9 - eta*dw9;
				System.out.println(df.format(w1)+" "+df.format(w2)+" "+df.format(w3)+" "+df.format(w4)+" "+df.format(w5)+" "+df.format(w6)+" "+df.format(w7)+" "+df.format(w8)+" "+df.format(w9));
				double evalError = 0;
				for(int k = 0; k < eval.length; k += 1) {
					x1 = eval[k][0]; x2 = eval[k][1]; y = eval[k][2];
					uA = w1 + x1*w2 + x2*w3;
					uB = x1*w5 + x2*w6 + w4;
					vA = Math.max(uA, 0);
					vB = Math.max(uB, 0);
					uC = vA*w8 + vB*w9 + w7;
					vC = 1/(1 + Math.exp(-uC));
					evalError += Math.pow(vC-y, 2)/2;
				}
				System.out.println(df.format(evalError));
			}
			break;
		case 700:
			eta = Double.parseDouble(args[10]);
			t = Double.parseDouble(args[11]);
			for(int m = 0; m < t; m += 1) {
				for(int n = 0; n < train.length; n += 1) {
					x1 = train[n][0]; x2 = train[n][1]; y = train[n][2];
					uA = w1 + x1*w2 + x2*w3;
					uB = x1*w5 + x2*w6 + w4;
					vA = Math.max(uA, 0);
					vB = Math.max(uB, 0);
					uC = vA*w8 + vB*w9 + w7;
					vC = 1/(1 + Math.exp(-uC));
					dEdvC =  vC - y;
					dEduC = dEdvC*(vC*(1 - vC));
					if(uA < 0) {dvAduA = 0;} else {dvAduA = 1;}
					dEdvA = w8*dEduC;
					dEduA = dEdvA*dvAduA;
					if(uB < 0) {dvBduB = 0;} else {dvBduB = 1;}
					dEdvB = w9*dEduC;
					dEduB = dEdvB*dvBduB;
					dw1 = dEduA;
					dw2 = x1*dEduA;
					dw3 = x2*dEduA;
					dw4 = dEduB;
					dw5 = x1*dEduB;
					dw6 = x2*dEduB;
					dw7 = dEduC;
					dw8 = vA*dEduC;
					dw9 = vB*dEduC;
					w1 = w1 - eta*dw1;
					w2 = w2 - eta*dw2;
					w3 = w3 - eta*dw3;
					w4 = w4 - eta*dw4;
					w5 = w5 - eta*dw5;
					w6 = w6 - eta*dw6;
					w7 = w7 - eta*dw7;
					w8 = w8 - eta*dw8;
					w9 = w9 - eta*dw9;
				}
				double evErr = 0;
				for(int o = 0; o < eval.length; o += 1) {
					x1 = eval[o][0]; x2 = eval[o][1]; y = eval[o][2];
					uA = w1 + x1*w2 + x2*w3;
					uB = x1*w5 + x2*w6 + w4;
					vA = Math.max(uA, 0);
					vB = Math.max(uB, 0);
					uC = vA*w8 + vB*w9 + w7;
					vC = 1/(1 + Math.exp(-uC));
					evErr += Math.pow(vC-y, 2)/2;
				}
				System.out.println(df.format(w1)+" "+df.format(w2)+" "+df.format(w3)+" "+df.format(w4)+" "+df.format(w5)+" "+df.format(w6)+" "+df.format(w7)+" "+df.format(w8)+" "+df.format(w9));
				System.out.println(df.format(evErr));
			}
			break;
		case 800:
			eta = Double.parseDouble(args[10]);
			t = Double.parseDouble(args[11]);
			double e = 0;
			for(int p = 0; p < t; p += 1) {
				for(int q = 0; q < train.length; q += 1) {
					x1 = train[q][0]; x2 = train[q][1]; y = train[q][2];
					uA = w1 + x1*w2 + x2*w3;
					uB = x1*w5 + x2*w6 + w4;
					vA = Math.max(uA, 0);
					vB = Math.max(uB, 0);
					uC = vA*w8 + vB*w9 + w7;
					vC = 1/(1 + Math.exp(-uC));
					dEdvC =  vC - y;
					dEduC = dEdvC*(vC*(1 - vC));
					if(uA < 0) {dvAduA = 0;} else {dvAduA = 1;}
					dEdvA = w8*dEduC;
					dEduA = dEdvA*dvAduA;
					if(uB < 0) {dvBduB = 0;} else {dvBduB = 1;}
					dEdvB = w9*dEduC;
					dEduB = dEdvB*dvBduB;
					dw1 = dEduA;
					dw2 = x1*dEduA;
					dw3 = x2*dEduA;
					dw4 = dEduB;
					dw5 = x1*dEduB;
					dw6 = x2*dEduB;
					dw7 = dEduC;
					dw8 = vA*dEduC;
					dw9 = vB*dEduC;
					w1 = w1 - eta*dw1;
					w2 = w2 - eta*dw2;
					w3 = w3 - eta*dw3;
					w4 = w4 - eta*dw4;
					w5 = w5 - eta*dw5;
					w6 = w6 - eta*dw6;
					w7 = w7 - eta*dw7;
					w8 = w8 - eta*dw8;
					w9 = w9 - eta*dw9;
				}
				double oldE = e;
				e = 0;
				for(int r = 0; r < eval.length; r += 1) {
					x1 = eval[r][0]; x2 = eval[r][1]; y = eval[r][2];
					uA = w1 + x1*w2 + x2*w3;
					uB = x1*w5 + x2*w6 + w4;
					vA = Math.max(uA, 0);
					vB = Math.max(uB, 0);
					uC = vA*w8 + vB*w9 + w7;
					vC = 1/(1 + Math.exp(-uC));
					e += Math.pow(vC-y, 2)/2;
				}
				if(p > 0 && oldE <= e) {
					System.out.println(p+1);
					System.out.println(df.format(w1)+" "+df.format(w2)+" "+df.format(w3)+" "+df.format(w4)+" "+df.format(w5)+" "+df.format(w6)+" "+df.format(w7)+" "+df.format(w8)+" "+df.format(w9));
					System.out.println(df.format(e));
					int score = 0;
					for(int s = 0; s < test.length; s += 1) {
						x1 = test[s][0]; x2 = test[s][1]; y = test[s][2];
						uA = w1 + x1*w2 + x2*w3;
						uB = x1*w5 + x2*w6 + w4;
						vA = Math.max(uA, 0);
						vB = Math.max(uB, 0);
						uC = vA*w8 + vB*w9 + w7;
						vC = 1/(1 + Math.exp(-uC));
						if(vC >= 0.5 && y == 1 || vC < 0.5 && y == 0) {
							score++;
						}
					}
					Double a = (double) score/test.length;
					System.out.println(df.format(a));
					break;
				}
			}
			break;		
		}
	}
	
}