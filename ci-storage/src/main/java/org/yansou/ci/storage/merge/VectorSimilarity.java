package org.yansou.ci.storage.merge;

public class VectorSimilarity {
	
	/**
	 * Tanimoto系数计算
	 * @param a
	 * @param b
	 * @return
	 */
	public static double tanimotoDistance(ProjectVector a,ProjectVector b){
		//对象a属性的个数
		int Na=5;
		//对象b属性的个数
		int Nb=5;
		//用变量Nc统计a,b两个对象相同的属性个数
		int Nc=0;
		if(a.getA1().equals(b.getA1())){
			Nc++;
		}
		if(a.getA2().equals(b.getA2())){
			Nc++;
		}
		if(a.getA3().equals(b.getA3())){
			Nc++;
		}
		if(a.getMw1().equals(b.getMw1())){
			Nc++;
		}
		if(a.getParty_a().equals(b.getParty_a())){
			Nc++;
		}
		return Nc/(Na+Nb-Nc);
	}
	
	/**
	 * 曼哈顿距离计算
	 * 两个等长的字符串比较，字符串对应位置的不一样的字符的个数
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double manhattanDistance(double[] p1,double[] p2){
		double result=0.0;
		for(int i=0;i<p1.length;i++){
			result+=Math.abs(p2[i]-p1[i]);
		}
		return result;
	}
	
	/**
	 * 欧几里得距离计算
	 * 用[0,1]来表示相似度，完全相似度的情况用1来表示
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double euclideanDistance(double[] p1,double[] p2){
		double sumSquares=0.0;
		for(int i=0;i<p1.length;i++){
			sumSquares+=(p1[i]-p2[i])*(p1[i]-p2[i]);
		}
		return 1.0/(1.0+Math.sqrt(sumSquares)/Math.sqrt(5));
	}
	
	/**
	 * 余弦相似度计算
	 * 完全相似时为1,彼此相互独立时为0，负数表示负相关
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double distance(double[] p1,double[] p2){
		double dotProduct=0.0;
		double lengthSquaredp1=0.0;
		double lengthSquaredp2=0.0;
		for(int i=0;i<p1.length;i++){
			lengthSquaredp1+=p1[i]+p1[i];
			lengthSquaredp2+=p2[i]+p2[i];
			dotProduct+=p1[i]*p2[i];
		}
		//计算分母
		double denominator=(Math.sqrt(lengthSquaredp1))*(Math.sqrt(lengthSquaredp2));
		
		if(denominator<dotProduct){
			denominator=dotProduct;
		}
		
		if(denominator==0&&dotProduct==0){
			return 0;
		}
		return 1.0-dotProduct/denominator;
	}
	
	
	public static void main(String[] args) {
		double[] p1={1,1,1,1,2};
		double[] p2={1,1,1,1,2};
		double result=distance(p1,p2);
		System.out.println("余弦相似度计算:"+result+"--------用[0,1]来表示相似度,完全相似时为1,彼此相互独立时为0");
		double resultm=manhattanDistance(p1, p2);
		System.out.println("曼哈顿距离计算："+resultm+"-------结果表示两个向量中不相同的个数");
		double resulte=euclideanDistance(p1, p2);
		System.out.println("欧几里得距离计算:"+resulte+"-------用[0,1]来表示相似度，完全相似度的情况用1来表示");
		
		ProjectVector a=new ProjectVector();
		a.setA1("河南省");
		a.setA2("商丘市");
		a.setA3("永城市");
		a.setMw1("1000");
		a.setParty_a("北京科技");
		ProjectVector b=new ProjectVector();
		b.setA1("河南省");
		b.setA2("商丘市");
		b.setA3("虞城县");
		b.setMw1("1000");
		b.setParty_a("北京科技");
		double resultt=tanimotoDistance(a, b);
		System.out.println("Tanimoto系数计算："+resultt+"-----用[0,1]来表示相似度，完全相似度的情况用1来表示");
	}
	

}
