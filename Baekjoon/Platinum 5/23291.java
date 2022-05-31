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
	public static void arrange(int[][] ch, int N, int K) {
		int answer =0 ;
		if(!inspect_ch(ch,N,K)) {
		while(true) {
//			System.out.println(1);
			min_plus(ch,N);
//			System.out.println(1);
			clock_turn(ch,N);
//			print(ch,N);
//			System.out.println(1);
			divd_ch(ch,N);
//			print(ch,N);
			up(ch,N);
//			print(ch,N);
			
			if(inspect_ch(ch,N,K)) break;
			answer++;
		}
		}
		System.out.println(answer+1);
	}
	public static void min_plus(int[][] ch,int N) {
		int min = Integer.MAX_VALUE;
		for(int i=0;i<N;i++) {
			min = Math.min(min, ch[N-1][i]);
		}
		for(int i=0;i<N;i++) {
			if(min==ch[N-1][i]) ch[N-1][i]++;
 		}
		ch[N-2][0] = ch[N-1][0];
		for(int i=0;i<N-1;i++) {
			ch[N-1][i] = ch[N-1][i+1];
			ch[N-1][i+1] = 0;
		}
	}
	public static void clock_turn(int[][] ch,int N) {
		while(true) {
		int i;
		for(i=0;i<N;i++) if(ch[N-2][i]==0) break;
		int count = 0;
		for(int j=i;j<N;j++) {
			if(ch[N-1][j]!=0) count++;
			else break;
		}
		int cnt = 0;
		for(int j=N-1;j>=0;j--) {
			if(ch[j][0]!=0) {
				count--;
				cnt++;
			}
			else break;
		}
		if(count<0) break;
		else {
			int[][] tmp = new int[i][cnt];
			for(int j=0;j<i;j++) {
				for(int k=0;k<cnt;k++) {
					tmp[j][k] = ch[N-1-k][j];
					ch[N-1-k][j] = 0;
				}
			}
			for(int j=0;j<N-i;j++) {
				ch[N-1][j] = ch[N-1][j+i];
				ch[N-1][j+i] = 0;
			}
			for(int j=0;j<i;j++) {
				for(int k=0;k<cnt;k++) {
					ch[N-1-i+j][k] = tmp[j][k];
				}
			}
		}
		}

	}
	public static void divd_ch(int[][] ch,int N) {
		int[][] tmp = new int[N][N];
		int[] ck = {1,0,-1,0};
		int[] cj = {0,1,0,-1};
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(ch[i][j]>0) {
					for(int k=0;k<4;k++) {
						int nx = i+ck[k];
						int ny = j+cj[k];
						if(nx>=0&&nx<N&&ny>=0&&ny<N&&ch[nx][ny]!=0&&ch[i][j]>ch[nx][ny]) {
							int t = (ch[i][j]-ch[nx][ny])/5;
							tmp[i][j] -= t;
							tmp[nx][ny] +=t;
						}
					}
				}
			}
		}
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				ch[i][j] += tmp[i][j];
			}
		}
		int[] tm = new int[N];
		int count = 0;
//		print(ch,N);
		for(int i=0;i<N;i++) {
			for(int j=N-1;j>=0;j--) {
				if(ch[j][i]==0) break;
				tm[count++] = ch[j][i];
				ch[j][i] = 0;
			}
		}
		for(int i=0;i<N;i++) ch[N-1][i] = tm[i];
	}
	public static void up(int[][] ch,int N) {
		for(int m=0;m<2;m++) {
//			print(ch,N);
			if(m==0) {
				int[] tmp = new int[N/2];
				for(int i=0;i<N/2;i++) tmp[i] = ch[N-1][i];
				for(int i=0;i<N/2;i++) {
					ch[N-1][i] = ch[N-1][i+N/2];
					ch[N-1][i+N/2] = 0;
				}
				for(int i=0;i<N/2;i++) ch[N-2][i] = tmp[N/2-1-i];
			}
			else {
				int[][] tmp = new int[2][N/4];
				for(int i=0;i<2;i++) {
					for(int j=0;j<N/4;j++) {
						tmp[i][j] = ch[N-1-i][N/4-1-j];
					}
				}
				for(int i=N-1;i>N-3;i--) {
					for(int j=0;j<N/4;j++) {
						ch[i][j] = ch[i][j+N/4];
						ch[i][j+N/4] = 0;
					}
				}
				for(int i=N-4;i<N-2;i++) {
					for(int j=0;j<N/4;j++) {
						ch[i][j] = tmp[i-(N-4)][j];
					}
				}
			}
		}
		divd_ch(ch,N);

	}
	public static boolean inspect_ch(int[][] ch,int N,int K) {
		int min = Integer.MAX_VALUE;
		int max = -1;
		for(int i=0;i<N;i++) {
			max = Math.max(max, ch[N-1][i]);
			min = Math.min(min, ch[N-1][i]);
		}
		if(max-min<=K) return true;
		else return false;
	}
	public static void print(int[][] ch,int N) {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(ch[i][j]+" ");
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
		int N = Integer.parseInt(std.nextToken());
		int K = Integer.parseInt(std.nextToken());
		std = new StringTokenizer(br.readLine());
		int[][] ch = new int[N][N];
		for(int i=0;i<N;i++) ch[N-1][i] = Integer.parseInt(std.nextToken());
		arrange(ch,N,K);

//		print(ch,N);
	}
}


