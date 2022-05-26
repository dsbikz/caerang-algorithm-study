import java.io.*;
import java.util.*;
class point implements Comparable<point>{
	int t,r,l,u,d;
	point(int t,int r,int l,int u,int d){
		this.t=t;
		this.r=r;
		this.l=l;
		this.u=u;
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
	public static void heat(int R,int C,ArrayList<point> list,point[][] ch, ArrayList<Edge> checklist,int K) {
		int count = 0;
		while(true) {
			for(int i=0;i<list.size();i++) {
				point tmp = list.get(i);
				if(tmp.t==1) heat_right(R,C,tmp.r,tmp.l,ch);
				if(tmp.t==2) heat_left(R,C,tmp.r,tmp.l,ch);
				if(tmp.t==3) heat_up(R,C,tmp.r,tmp.l,ch);
				if(tmp.t==4) heat_down(R,C,tmp.r,tmp.l,ch);
			}
			Adjust_temparature(ch,R,C);
			Outside_minus(ch,R,C);
			count++;
			if(count>100) {
				System.out.println(101); return;
			}
			if(Inspect_ch(ch,R,C,checklist,K)) {
				System.out.println(count);
				return;
			}
		}
	}
	public static void heat_up(int R,int C,int x,int y, point[][] ch) {
		int[][] check = new int[R+1][C+1];
		Queue<Edge> Q = new LinkedList<>();
		Q.offer(new Edge(x,y));
		int count = 4;
		while(!Q.isEmpty()) {
			Edge tmp = Q.poll();
			if(tmp.x-1>=1) {
			Q.offer(new Edge(tmp.x-1,tmp.y));
			ch[tmp.x-1][tmp.y].t += 5;
			}
			break;
		}
		while(!Q.isEmpty()) {
			int len = Q.size();
			for(int i=0;i<len;i++) {
				Edge tmp = Q.poll();
				if(tmp.y-1>=1&&ch[tmp.x][tmp.y].l != 1 && check[tmp.x][tmp.y-1]==0) {
					Q.offer(new Edge(tmp.x,tmp.y-1)); 
					check[tmp.x][tmp.y-1] = 1;
				}
				if(tmp.y+1<=C&&ch[tmp.x][tmp.y].r != 1&&check[tmp.x][tmp.y+1]==0) {
					Q.offer(new Edge(tmp.x,tmp.y+1));
					check[tmp.x][tmp.y+1] = 1;
				}
				if(check[tmp.x][tmp.y] != 1) {
					check[tmp.x][tmp.y] = 1;
					Q.offer(tmp);
				}
			}
			len = Q.size();
			for(int i=0;i<len;i++) {
				Edge tmp = Q.poll();
				if(tmp.x-1>=1&&ch[tmp.x][tmp.y].u !=1 ) {
					Q.offer(new Edge(tmp.x-1,tmp.y));
					ch[tmp.x-1][tmp.y].t += count;
				}
			}
			count--;
			if(count==0) break;
		}
	}
	public static void heat_left(int R,int C,int x,int y,point[][] ch) {
		int[][] check = new int[R+1][C+1];
		Queue<Edge> Q = new LinkedList<>();
		Q.offer(new Edge(x,y));
		int count = 4;
		while(!Q.isEmpty()) {
			Edge tmp = Q.poll();
			if(tmp.y-1>=1) {
				Q.offer(new Edge(tmp.x,tmp.y-1));
				ch[tmp.x][tmp.y-1].t += 5;
			}
			break;
		}
		while(!Q.isEmpty()) {
			int len = Q.size();
			for(int i=0;i<len;i++) {
				Edge tmp = Q.poll();
				if(tmp.x-1>=1&&ch[tmp.x][tmp.y].u != 1&&check[tmp.x-1][tmp.y] == 0) {
					Q.offer(new Edge(tmp.x-1,tmp.y));
					check[tmp.x-1][tmp.y] = 1;
				}
				if(tmp.x+1<=R&&ch[tmp.x][tmp.y].d != 1&&check[tmp.x+1][tmp.y] == 0) {
					Q.offer(new Edge(tmp.x+1,tmp.y));
					check[tmp.x+1][tmp.y] =1 ;
				}
				if(check[tmp.x][tmp.y] == 0) {
					Q.offer(tmp);
					check[tmp.x][tmp.y] = 1;
				}
			}
			len = Q.size();
			for(int i=0;i<len;i++) {
				Edge tmp = Q.poll();
				if(tmp.y-1>=1&&ch[tmp.x][tmp.y].l != 1) {
					Q.offer(new Edge(tmp.x,tmp.y-1));
					ch[tmp.x][tmp.y-1].t += count;
				}
			}
			count--;
			if(count==0) break;
		}
	}
	public static void heat_right(int R,int C,int x,int y, point[][] ch) {
		int[][] check = new int[R+1][C+1];
		Queue<Edge> Q = new LinkedList<>();
		Q.offer(new Edge(x,y));
		int count = 4;
		while(!Q.isEmpty()) {
			Edge tmp = Q.poll();
			if(tmp.y+1<=C) {
				Q.offer(new Edge(tmp.x,tmp.y+1));
				ch[tmp.x][tmp.y+1].t += 5;
			}
			break;
		}
		while(!Q.isEmpty()) {
			int len = Q.size();
			for(int i=0;i<len;i++) {
				Edge tmp = Q.poll();
				if(tmp.x-1>=1&&ch[tmp.x][tmp.y].u != 1&& check[tmp.x-1][tmp.y] == 0) {
					Q.offer(new Edge(tmp.x-1,tmp.y));
					check[tmp.x-1][tmp.y] = 1;
				}
				if(tmp.x+1<=R&&ch[tmp.x][tmp.y].d != 1&& check[tmp.x+1][tmp.y] == 0) {
					Q.offer(new Edge(tmp.x+1,tmp.y));
					check[tmp.x+1][tmp.y] = 1;
				}
				if(check[tmp.x][tmp.y] == 0) {
					check[tmp.x][tmp.y] = 1;
					Q.offer(tmp);
				}
			}
			len = Q.size();
			for(int i=0;i<len;i++) {
				Edge tmp = Q.poll();
				if(tmp.y+1<=C&&ch[tmp.x][tmp.y].r != 1) {
					ch[tmp.x][tmp.y+1].t += count;
					Q.offer(new Edge(tmp.x,tmp.y+1));
				}
			}
			count--;
			if(count==0) break;
		}
	}
	public static void heat_down(int R,int C,int x,int y, point[][] ch) {
		int[][] check = new int[R+1][C+1];
		Queue<Edge> Q = new LinkedList<>();
		Q.offer(new Edge(x,y));
		int count = 4;
		while(!Q.isEmpty()) {
			Edge tmp = Q.poll();
			if(tmp.x+1<=R) {
			Q.offer(new Edge(tmp.x+1,tmp.y));
			ch[tmp.x+1][tmp.y].t += 5;
			}
			break;
		}
		while(!Q.isEmpty()) {
			int len = Q.size();
			for(int i=0;i<len;i++) {
				Edge tmp = Q.poll();
				if(tmp.y+1<=C&&ch[tmp.x][tmp.y].r != 1&& check[tmp.x][tmp.y+1]==0) {
					Q.offer(new Edge(tmp.x,tmp.y+1));
					check[tmp.x][tmp.y+1] = 1;
				}
				if(tmp.y-1>=1&&ch[tmp.x][tmp.y].l != 1&& check[tmp.x][tmp.y-1]==0) {
					Q.offer(new Edge(tmp.x,tmp.y-1));
					check[tmp.x][tmp.y-1] = 1;
				}
				if(check[tmp.x][tmp.y] != 1) {
					check[tmp.x][tmp.y] = 1;
					Q.offer(tmp);
				}
			}
			len = Q.size();
			for(int i=0;i<len;i++) {
				Edge tmp = Q.poll();
				if(tmp.x+1<=R&&ch[tmp.x][tmp.y].d != 1) {
					Q.offer(new Edge(tmp.x+1,tmp.y));
					ch[tmp.x+1][tmp.y].t += count;
				}
			}
			count--;
			if(count==0) break;
		}
	}
	public static void Adjust_temparature(point[][] ch,int R,int C) {
		int[][] check = new int[R+1][C+1];
		for(int i=1;i<=R;i++) {
			for(int j=1;j<=C;j++) {
				if(ch[i][j].t>0) {
					int nx = i+1;
					int ny = j;
					if(nx>=1&&nx<=R&&ny>=1&&ny<=C&&ch[i][j].d!=1&&ch[i][j].t>ch[nx][ny].t) {
						int tmp = ch[i][j].t-ch[nx][ny].t;
						tmp = tmp/4;
						check[nx][ny] += tmp;
						check[i][j] -= tmp;
					}
					nx = i-1;
					ny = j;
					if(nx>=1&&nx<=R&&ny>=1&&ny<=C&&ch[i][j].u!=1&&ch[i][j].t>ch[nx][ny].t) {
						int tmp = ch[i][j].t-ch[nx][ny].t;
						tmp = tmp/4;
						check[nx][ny] += tmp;
						check[i][j] -= tmp;
					}
					nx = i;
					ny = j+1;
					if(nx>=1&&nx<=R&&ny>=1&&ny<=C&&ch[i][j].r!=1&&ch[i][j].t>ch[nx][ny].t) {
						int tmp = ch[i][j].t-ch[nx][ny].t;
						tmp = tmp/4;
						check[nx][ny] += tmp;
						check[i][j] -= tmp;
					}
					nx = i;
					ny = j-1;
					if(nx>=1&&nx<=R&&ny>=1&&ny<=C&&ch[i][j].l!=1&&ch[i][j].t>ch[nx][ny].t) {
						int tmp = ch[i][j].t-ch[nx][ny].t;
						tmp = tmp/4;
						check[nx][ny] += tmp;
						check[i][j] -= tmp;
					}
				}
			}
		}
		for(int i=1;i<=R;i++) {
			for(int j=1;j<=C;j++) {
				ch[i][j].t += check[i][j];
			}
		}
	}
	public static void Outside_minus(point[][] ch,int R,int C) {
		for(int i=1;i<=R;i++) {
			if(ch[i][1].t>0) ch[i][1].t--;
			if(ch[i][C].t>0) ch[i][C].t--;
		}
		for(int i=2;i<=C-1;i++) {
			if(ch[1][i].t>0) ch[1][i].t--;
			if(ch[R][i].t>0) ch[R][i].t--;
		}
	}
	public static boolean Inspect_ch(point[][] ch,int R,int C,ArrayList<Edge> checklist,int K) {
		int count = 0;
		for(int i=0;i<checklist.size();i++) {
			Edge tmp = checklist.get(i);
			if(ch[tmp.x][tmp.y].t>=K) count++;
		}
		if(count==checklist.size()) return true;
		else return false;
	}
	public static void print(int R,int C,point[][] ch) {
		for(int i=1;i<=R;i++) {
			for(int j=1;j<=C;j++) {
				System.out.print(ch[i][j].t+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static void main(String[]args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer std = new StringTokenizer(br.readLine());
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int R = Integer.parseInt(std.nextToken());
		int C = Integer.parseInt(std.nextToken());
		int K = Integer.parseInt(std.nextToken());
		point[][] ch = new point[R+1][C+1];
		ArrayList<point> list = new ArrayList<>();
		ArrayList<Edge> checklist = new ArrayList<>();
		for(int i=1;i<=R;i++) {
			std = new StringTokenizer(br.readLine());
			for(int j=1;j<=C;j++) {
				int tmp = Integer.parseInt(std.nextToken());
				ch[i][j] = new point(0,0,0,0,0);
				if(tmp==1||tmp==2||tmp==3||tmp==4) list.add(new point(tmp,i,j,0,0));
				if(tmp==5) checklist.add(new Edge(i,j));
				
			}
		}
		std = new StringTokenizer(br.readLine());
		int W = Integer.parseInt(std.nextToken());
		for(int i=0;i<W;i++) {
			std = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(std.nextToken());
			int y = Integer.parseInt(std.nextToken());
			int t = Integer.parseInt(std.nextToken());
			if(t==1) {
				ch[x][y].r = 1;
				ch[x][y+1].l = 1;
				
			}
			else {
				ch[x][y].u = 1;
				ch[x-1][y].d = 1;
			}
		}
		heat(R,C,list,ch,checklist,K);
	
	}	
}


