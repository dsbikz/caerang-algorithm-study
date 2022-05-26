import java.io.*;
import java.util.*;
class point implements Comparable<point>{
	int x,y,d;
	point(int x,int y,int d){
		this.x=x;
		this.y=y;
		this.d=d;
	}
	@Override
	public int compareTo(point o) {
		return 1;
	}
}
class Edge{
	int x,y;
	Edge(int x,int y){
		this.x=x;
		this.y=y;
	}
}
public class Main{
	static int hap = -1;
	static int hap_t = 10000;
	public static void shark(Queue<Integer>[][] Q,int S,Queue<Integer>[][] copy,int shark_x,int shark_y) {
		int[][] check = new int[5][5];
		int[][] ch = new int[5][5];
		int answer = 0;
		int[] ck = {-1,0,1,0};
		int[] cj = {0,-1,0,1};
		for(int i=0;i<S;i++) {
			copy(Q,copy);
			fish_move(Q,check,shark_x,shark_y);
			shark_move(Q,0,shark_x,shark_y,0,ck,cj,0,100,ch);
			int l = 100;
			for(int j=0;j<3;j++) {
				int nx = shark_x+ck[hap_t/l-1];
				int ny = shark_y+cj[hap_t/l-1];
				if(Q[nx][ny].size()>0) check[nx][ny] = 3;
				Q[nx][ny].clear();
				hap_t = hap_t%l;
				l = l/10;
				shark_x = nx;
				shark_y = ny;
			}
			hap = -1;
			hap_t = -1;
			smell_minus(check);
			
			shark_copy(Q,copy);
		}
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				answer += Q[i][j].size();
			}
		}
		System.out.println(answer);
	}
	public static void copy(Queue<Integer>[][] Q, Queue<Integer>[][] copy) {
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				if(Q[i][j].size()>0) {
					for(int k=0;k<Q[i][j].size();k++) {
						int tmp = Q[i][j].poll();
						copy[i][j].offer(tmp);
						Q[i][j].offer(tmp);
					}
				}
			}
		}
	}
	public static void fish_move(Queue<Integer>[][] Q,int[][] check,int shark_x,int shark_y) {
		int[] ch = {0,-1,-1,-1,0,1,1,1};
		int[] ck = {-1,-1,0,1,1,1,0,-1};
		int[][] tmp = new int[5][5];
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				tmp[i][j] = Q[i][j].size();
			}
		}
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				if(Q[i][j].size()>0) {
					for(int l=0;l<tmp[i][j];l++) {
						int d = Q[i][j].poll()-1;
						int c = d;
						for(int k=0;k<8;k++) {
							if(d==-1) d = 7;
							int nx = i+ch[d];
							int ny = j+ck[d];
							if(nx>=1&&nx<5&&ny>=1&&ny<5&&!(nx==shark_x&&ny==shark_y)&&check[nx][ny]==0) {
								Q[nx][ny].offer(d+1);
								c = -1;
								break;
							}
							d--;
						}
						if(c==-1) continue;
						else Q[i][j].offer(c+1);
					}
				}
			}
		}
		}
	public static void shark_move(Queue<Integer>[][] Q,int sum, int shark_x,int shark_y,int k,int[] ck,int[] cj,int t,int l,int[][] ch) {
		if(k==3) {
			if(hap<sum) {
				hap = sum;
				hap_t = t;
			}
			else if(hap==sum) {
				if(hap_t>t) {
					hap_t = t;
					hap = sum;
				}
			}
		}
		else {
			for(int i=0;i<4;i++) {
				int nx = shark_x + ck[i];
				int ny = shark_y + cj[i];
				if(nx>=1&&nx<5&&ny>=1&&ny<5) {
					int check = 0;
					if(ch[nx][ny]==0) {
					sum+=Q[nx][ny].size();
					ch[nx][ny] = 1;
					check = 1;
					}
					shark_move(Q,sum,nx,ny,k+1,ck,cj,t+(i+1)*l,l/10,ch);
					if(check == 1) {
						ch[nx][ny] = 0;
						sum -= Q[nx][ny].size();
					}
				}
			}
		}
	}
	public static void smell_minus(int[][] check) {
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				if(check[i][j]>0) check[i][j]--;
			}
		}
	}
	public static void shark_copy(Queue<Integer>[][] Q, Queue<Integer>[][] copy) {
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				while(!copy[i][j].isEmpty()) {
					Q[i][j].offer(copy[i][j].poll());
				}
			}
		}
	}
	public static void print(Queue<Integer>[][] Q) {
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				System.out.print(Q[i][j].size()+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static void cprint(int[][] check) {
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				System.out.print(check[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static void main(String[]args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer std = new StringTokenizer(br.readLine());
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder str1 = new StringBuilder();
		int M = Integer.parseInt(std.nextToken());
		int S = Integer.parseInt(std.nextToken());
		Queue<Integer>[][] Q = new LinkedList[5][5];
		Queue<Integer>[][] copy = new LinkedList[5][5];
		for(int i=1;i<5;i++) {
			for(int j=1;j<5;j++) {
				Q[i][j] = new LinkedList<>();
				copy[i][j] = new LinkedList<>();
			}
		}
		for(int i=0;i<M;i++) {
			std = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(std.nextToken());
			int y = Integer.parseInt(std.nextToken());
			int d = Integer.parseInt(std.nextToken());
			Q[x][y].offer(d);
		}
		std = new StringTokenizer(br.readLine());
		int shark_x = Integer.parseInt(std.nextToken());
		int shark_y = Integer.parseInt(std.nextToken());
		shark(Q,S,copy,shark_x,shark_y);
		
	}
}


