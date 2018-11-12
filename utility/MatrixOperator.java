package utility;

import java.text.DecimalFormat;

public class MatrixOperator {
	private MatrixOperator(){}
	
	public static double determinant(double[][] matrix){
		if(matrix.length == 0)
			throw new IllegalArgumentException("matrix.length == 0");
		for(int i=0; i<matrix.length; i++)
			if(matrix[i].length != matrix.length)
				throw new IllegalArgumentException("It is not a square matrix");
		if(matrix.length == 1)
			return matrix[0][0];
		if(matrix.length == 2)
			return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
		return innerDeterminant(matrix);
	}
	
	private static double innerDeterminant(double[][] matrix){
		printMatrix(matrix);
		double result = 1;
		double [] temp = null;
		double ratio = 0;
		for(int i=0; i<matrix.length; i++){
			if(matrix[i][i] == 0){
				System.out.println("Because matrix["+i+"]["+i+"] == 0");
				matrix = changeDeterminantNoZero(matrix, i, i);
				printMatrix(matrix);
				result *= -1;
			}
			for(int j=0; j<i; j++){
				if(matrix[i][j] == 0){
					continue;
				}
				System.out.println("matrix["+j+"]["+j+"] == "+matrix[j][j]);
				if (matrix[j][j]==0) {  
					System.out.println("It equals 0, exchange matrix["+i+"] and matrix["+(i-1)+"]");
                    temp = matrix[i];  
                    matrix[i] = matrix[i-1];  
                    matrix[i-1] = temp;  
                    result*=-1;  
                    continue;  
                }
				ratio = -(matrix[i][j]/matrix[j][j]);
				System.out.println("-matrix["+i+"]["+j+"]/matrix["+j+"]["+j+"] == -("+matrix[i][j]+"/"+matrix[j][j]+") == "+ratio);
				matrix[i] = addValue(matrix[i],matrix[j],ratio);
				printMatrix(matrix);
			}
		}
		DecimalFormat df = new DecimalFormat(".##");  
        return Double.parseDouble(df.format(mathValue(matrix,result)));
	}
	
	private static double mathValue(double[][] value, double result){  
	       for (int i = 0; i < value.length; i++) {  
	           if (value[i][i]==0) {  
	               return 0;  
	           }  
	           result *= value[i][i];  
	       }  
	       return result;  
	} 
	
	private static double[] addValue(double[] currentRow, double[] frontRow, double ratio){  
        for (int i = 0; i < currentRow.length; i++) {  
            currentRow[i] += frontRow[i]*ratio;  
        }  
        return currentRow;  
    } 
	
	private static double[][] changeDeterminantNoZero(double[][] matrix, int line, int row){
		double[] temp = null;
		System.out.println("changeDeterminantNoZero():");
		for(int i=line; i<matrix.length; i++){
			System.out.println("i == "+i);
			System.out.println("matrix["+i+"]["+row+"] == "+matrix[i][row]);
			if(matrix[i][row] != 0){
				System.out.println("dont equal 0, exchange matrix["+line+"] and matrix["+i+"]");
				temp = matrix[line];
				matrix[line] = matrix[i];
				matrix[i] = temp;
				return matrix;
			}
		}
		return matrix;
	}
	
	private static void printMatrix(double[][] matrix){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<matrix.length; i++){
			sb.append("|");
			for(int j=0; j<matrix[i].length; j++){
				sb.append(matrix[i][j]);
				if(j<matrix[i].length-1){
					sb.append(", ");
				}
			}
			sb.append("|"+System.lineSeparator());
		}
		System.out.println(sb.toString());
	}
	/**
	 * 矩阵乘法<br>
	 * 若左矩阵的列数不等于右矩阵的行数,返回null<br>
	 * 若矩阵每行的列数不等,返回null
	 * @param left 左矩阵
	 * @param right 右矩阵
	 * @return
	 */
	public static double[][] matrixMultiply(double[][] left, double[][] right){
		for(int i=0; i<left.length; i++)
			if(left[i].length != right.length)
				return null;
		int rightColumnNumber = right[0].length;
		for(int i=1; i<right.length; i++)
			if(right[i].length != rightColumnNumber)
				return null;
		double[][] result = new double[left.length][rightColumnNumber];
		for(int i=0; i<result.length; i++){
			for(int j=0; j<result[i].length; j++){
				for(int k=0; k<right.length; k++){
					result[i][j] += left[i][k] * right[k][j];
				}
			}
		}
		return result;
	}
}