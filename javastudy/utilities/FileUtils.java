package javastudy.utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * 工具类，可以读取文件、写入文件，
 * 该类还继承ArrayList，创建对象后，可将文件内容按分割符分割后存入集合
 */
public class FileUtils {
	/**
	 * 判断所给新文件路径是否正确
	 *
	 * @param newFile 新文件
	 * @return 路径正确就返回true，不存在就返回false
	 */
	public static boolean checkNewFile(File newFile) {
		try {
			return newFile.getCanonicalFile().getParentFile().isDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 判断所给新文件路径是否正确
	 *
	 * @param newFile 新文件
	 * @return 路径正确就返回true，不存在就返回false
	 */
	public static boolean checkNewFile(String newFile) {
		return checkNewFile(new File(newFile));
	}

	/**
	 * 读取一个文件中所有文本信息
	 *
	 * @param fileName 文件名
	 * @return 文本内容
	 */
	public static String read(String fileName) throws RuntimeException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()))) {
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s);
				sb.append("\n");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	/**
	 * 读取二进制文件
	 *
	 * @param file 文件对象
	 * @return 二进制数据
	 * @throws RuntimeException 读取失败
	 */
	public static byte[] readBinary(File file) throws RuntimeException {
		try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
			byte[] bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
			return bytes;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 读取二进制文件
	 *
	 * @param fileName 文件全路径
	 * @return 二进制数据
	 * @throws RuntimeException 文件不存在
	 */
	public static byte[] readBinary(String fileName) throws RuntimeException {
		File file = new File(fileName);
		if (!file.isFile())
			throw new RuntimeException("不是文件");
		return readBinary(file);
	}

	/**
	 * 读取Blob对象中数据并返回byte数组
	 *
	 * @param file 要读取的Blob对象
	 * @return byte数据
	 * @throws RuntimeException 读取、关闭异常
	 */
	public static byte[] blobToType(Blob file) throws RuntimeException {
		if (file == null) throw new NullPointerException();
		try (InputStream is = file.getBinaryStream();
		     ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			int len = -1;
			byte[] buf = new byte[1024];

			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			return baos.toByteArray();
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 向一个文件写入内容
	 *
	 * @param fileName 文件路径
	 * @param text     文本内容
	 * @throws RuntimeException 失败
	 */
	public static void write(String fileName, String text) throws RuntimeException {
		try (PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile())) {
			out.print(text);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 拷贝文件到指定位置
	 *
	 * @param source 要拷贝的文件
	 * @param target 新文件指定位置
	 * @param append 选择是覆盖还是追加信息
	 */
	public static void copyFile(String source, String target,boolean append) throws RuntimeException {
		if (!new File(source).isFile())
			throw new RuntimeException("文件不存在");
		if (!checkNewFile(new File(target)))
			throw new RuntimeException("目标文件路径不正确");
		try (FileChannel fic = new FileInputStream(source).getChannel();
		     FileChannel foc = new FileOutputStream(target,append).getChannel()) {
			fic.transferTo(0, fic.size(), foc);
			foc.transferFrom(fic, 0, fic.size());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 字节数组转BufferedImage对象
	 *
	 * @param imageData 字节数组
	 * @return BufferedImage对象
	 */
	public static BufferedImage byteToImage(byte[] imageData) throws RuntimeException {
		BufferedImage image = null;
		try (ByteArrayInputStream byteInputStream = new ByteArrayInputStream(imageData)) {//将b作为输入流；
			return ImageIO.read(byteInputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * BufferedImage对象转字节数组
	 *
	 * @param imageData BufferedImage对象
	 * @param suffix    图像后缀
	 * @return 字节数组
	 */
	public static byte[] imageToByte(BufferedImage imageData, String suffix) throws RuntimeException {
		byte[] imageDate = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(imageData, suffix, baos);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 读取数组中数据并保存到电脑上
	 *
	 * @param fileData 字节数组
	 * @param filePath 要保存文件的全路径
	 * @return 文件保存成功与否
	 */
	public static boolean byteToFile(byte[] fileData, String filePath) throws RuntimeException {
		if (!checkNewFile(new File(filePath)))
			throw new RuntimeException("文件路径不存在");
		try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
			outputStream.write(fileData);
			outputStream.flush();
			return true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}