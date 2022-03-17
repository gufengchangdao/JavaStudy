package javastudy.iostream_.nio_;

import java.nio.*;

/**
 * 视图缓冲器读取信息的简单表示
 */
public class ViewBuffers {
	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 0, 'a'}); //大小正好为8个字节

		while (bb.hasRemaining()) {
			System.out.printf("%10d", bb.get());
		}
		System.out.println();
		bb.rewind();

		CharBuffer charBuffer = bb.asCharBuffer();
		while (charBuffer.hasRemaining()) {
			System.out.printf("%10c", charBuffer.get());
		}
		System.out.println();
		bb.rewind();

		ShortBuffer shortBuffer = bb.asShortBuffer();
		while (shortBuffer.hasRemaining()) {
			System.out.printf("%10d", shortBuffer.get());
		}
		System.out.println();
		bb.rewind();

		IntBuffer intBuffer = bb.asIntBuffer();
		while (intBuffer.hasRemaining()) {
			System.out.printf("%10d", intBuffer.get());
		}
		System.out.println();
		bb.rewind();

		FloatBuffer floatBuffer = bb.asFloatBuffer();
		while (floatBuffer.hasRemaining()) {
			System.out.printf("%10.3e", floatBuffer.get());
		}
		System.out.println();
		bb.rewind();

		LongBuffer longBuffer = bb.asLongBuffer();
		while (longBuffer.hasRemaining()) {
			System.out.printf("%10d", longBuffer.get());
		}
		System.out.println();
		bb.rewind();

		DoubleBuffer doubleBuffer = bb.asDoubleBuffer();
		while (doubleBuffer.hasRemaining()) {
			System.out.printf("%10e.3", doubleBuffer.get());
		}
		System.out.println();
		bb.rewind();
	}
}
/*
         0         0         0         0         0         0         0        97
                                       a
         0         0         0        97
         0        97
 0.000e+00 1.359e-43
        97
4.800000e-322.3
TODO 结果解析：
 使用ByteBuffer.wrap()获取了8个字节大小的ByteBuffer，并用不同类型视频缓冲器读取
 ByteBuffer中存入的是字节，当换成各种基本类型的视图缓冲器读取时，数据显示的方式也不同，比如double的缓冲器就一次读了8个字节
*/