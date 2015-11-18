
import java.util.Arrays;

public class Matrix {
	public static native void multiplyC(double[] a, double[] b, double[] r, int i, int j, int k);

	public static native void powerC(double[] a, double[] r, int i, int times);

	static {
		System.loadLibrary("NativeFunctions");
	}

	private double matrixArr[];

	private int width;
	private int height;

	public static void main(String[] args) {
		// Test multiply
		Matrix a = new Matrix(400, 6000);
		Matrix b = new Matrix(6000, 300);
		Matrix c = new Matrix(200, 200);
		int exp = 51;
		Matrix javaPow;
		Matrix natPow;
		// class clock from algd2
		Clock ca = new Clock();
		ca.start();
		javaPow = c.power(exp);
		ca.stop();
		System.out.println("Java power takes: " + ca.getMillis() + "ms");
		ca.start();
		natPow = c.powerNative(exp);
		ca.stop();
		System.out.println("Native power takes: " + ca.getMillis() + "ms");
		
		if (javaPow.equals(natPow))
			System.out.println("Power-matrices are equal");
		else
			System.out.println("Power-matrices are not equal");
		
		ca.start();
		Matrix javaMul = a.multiply(b);
		ca.stop();
		System.out.println("Java multiply takes: " + ca.getMillis() + "ms");

		ca.start();
		Matrix natMul = a.multiplyNative(b);
		ca.stop();
		System.out.println("Native multiply takes: " + ca.getMillis() + "ms");

		if (javaMul.equals(natMul))
			System.out.println("Multiply-matrices are equal");
		else
			System.out.println("Multiply-matrices are not equal");


		

		
		
		// Typische ausgabe:
		/*
		 * Java multiply takes: 689ms 
		 * Native multiply takes: 712ms 
		 * Multiply-matrices are equal
		 * Java power takes: 352ms
		 * Native power takes: 329ms 
		 * Power-matrices are equal
		 */

	}

	public Matrix(double[] a, int h, int w) {
		matrixArr = a;
		height = h;
		width = w;
	}

	public Matrix(int h, int w) {
		this.matrixArr = new double[h * w];
		this.height = h;
		this.width = w;
		for (int i = 0; i < matrixArr.length; i++) {
			matrixArr[i] = Math.random();
		}
	}
	public Matrix(int h, int w, int fillwith) {
		this.matrixArr = new double[h * w];
		this.height = h;
		this.width = w;
		for (int i = 0; i < matrixArr.length; i++) {
			matrixArr[i] =fillwith;
		}
	}

	public Matrix power(int k) {
		Matrix m = new Matrix(this.matrixArr, this.height, this.width);
		for (int i = 0; i < k - 1; i++) {
			m = m.multiply(this);
		}
		return m;
	}

	public Matrix multiply(Matrix m) {

		double[] a = matrixArr;
		double[] b = m.matrixArr;
		int bW = m.width;
		double output[] = new double[height * bW];
		int index = 0;
		for (int y = 0; y < height; ++y) {
			int index1 = 0;
			for (int x = 0; x < bW; ++x, ++index, ++index1) {				
				double total = 0.0;
				int index2 = 0;
				for (int i = 0; i < width; ++i) {
					total += a[i + index1] * b[x + index2];
				}
				index2 += bW;
				output[index] = total;
			}
		}
		return new Matrix(output, height, bW);
	}

	Matrix multiplyNative(Matrix m) {
		double[] ret = new double[this.height * m.width];
		multiplyC(matrixArr, m.matrixArr, ret, this.height, this.width, m.width);
		return new Matrix(ret, this.height, m.width);
	}

	Matrix powerNative(int exp) {
		double[] ret = new double[this.height * this.width];
		powerC(matrixArr, ret, this.height, exp);
		return new Matrix(ret, this.height, this.width);
	}

	boolean equals(Matrix m) {
		return (m.height == this.height && m.width == this.width && Arrays.equals(m.matrixArr, this.matrixArr));
	}
}
