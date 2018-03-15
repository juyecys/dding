package cn.com.dingduoduo.utils.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author xiaqiang
 *
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 读取文件内容
	 *
	 * @param path
	 *            文件路径
	 * @return
	 */
	public static String readFileAsString(String path) {
		BufferedReader reader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(path);
			reader = new BufferedReader(fileReader);
			String row;
			StringBuffer sqlBuf = new StringBuffer("");
			while ((row = reader.readLine()) != null) {
				sqlBuf.append(row + "\n");
			}
			reader.close();
			return sqlBuf.toString();
		} catch (Exception e) {
			logger.error("readFileAsString exception:{}", e);
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					logger.error("fileReader close exception:{}", e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("readFileAsString reader close exception:{}", e);
				}
			}
		}
		return null;
	}

	/**
	 * 向文件中添加内容
	 *
	 * @param file
	 *            文件对象
	 * @param content
	 *            新添加的内容
	 */
	public static void write(File file, String content) {
		FileWriter out;
		try {
			out = new FileWriter(file, true);
			out.write(content);
			out.close();
		} catch (Exception e) {
			logger.error("write exception:{}", e);
		}
	}

	/**
	 * 判断文件对象是否图片类型
	 */
	public static boolean isImageFile(Object object) {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(object);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return false;
			}
		} catch (IOException e) {
			logger.error("isImageFile exception:{}", e);
			return false;
		} finally {
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					logger.error("isImageFile ImageInputStream close exception:{}", e);
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 复制单个文件
	 */
	public static void copyFile(String oldPath, String newPath) {
		copyFileAndDelete(oldPath, newPath, false);
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            源文件路径
	 * @param newPath
	 *            目标文件路径
	 * @param delete
	 *            复制后是否删除源文件
	 */
	public static void copyFileAndDelete(String oldPath, String newPath, boolean delete) {
		int byteread = 0;
		File oldFile = new File(oldPath);
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			if (oldFile.exists()) {
				fin = new FileInputStream(oldFile);
				fout = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = fin.read(buffer)) != -1) {
					fout.write(buffer, 0, byteread);
				}
				fin.close();
				fout.close();
				if (delete) {
					oldFile.delete();
				}
			} else {
				throw new Exception("复制的文件不存在!");
			}
		} catch (Exception e) {
			logger.error("copyFileAndDelete exception:{}", e);
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (IOException e) {
				logger.error("FileInputStream close exception:{}", e);
			}
		}
	}

	public static void copyFileAndDelete(InputStream is, String newPath, boolean delete) {
		int byteread = 0;
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(newPath);
			byte[] buffer = new byte[1024];
			while ((byteread = is.read(buffer)) != -1) {
				fout.write(buffer, 0, byteread);
			}
			is.close();
			fout.close();
		} catch (Exception e) {
			logger.error("copyFileAndDelete close exception:{}", e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (IOException e) {
				logger.error("FileOutputStream and FileInputStream close exception:{}", e);
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 */
	public static void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtend(String filename) {
		return getExtend(filename, "");
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtend(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return (filename.substring(i + 1)).toLowerCase();
			}
		}
		return defExt.toLowerCase();
	}

	/**
	 * 获取文件扩展名(带逗号,即.jpg)
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtendWithDot(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return (filename.substring(i)).toLowerCase();
			}
		}
		return defExt.toLowerCase();
	}

	/**
	 * 获取文件扩展名(带逗号,即.jpg)
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtendWithDot(String filename) {
		return getExtendWithDot(filename, "");
	}

	/**
	 * 获取文件名称[不含后缀名]
	 * 
	 * @param
	 * @return String
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return splitIndex == -1 ? fileName : fileName.substring(0, splitIndex).replaceAll("\\s*", "");
	}

	/**
	 * 获取文件名称[不含后缀名] 不去掉文件目录的空格
	 * 
	 * @param
	 * @return String
	 */
	public static String getFilePrefix2(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return splitIndex == -1 ? fileName : fileName.substring(0, splitIndex);
	}

	/**
	 * 判断文件是否为图片
	 * 
	 * @param filename
	 *            文件名
	 * @return 检查后的结果
	 */
	public static boolean isPicture(String filename) {
		// 文件名称为空的场合
		if (StringUtil.isEmpty(filename)) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		String tmpName = getExtend(filename);
		// 声明图片后缀名数组
		String imgeArray[] = { "bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico" };
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (imgeArray[i].equalsIgnoreCase(tmpName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除指定的文件
	 * 
	 * @param strFileName
	 *            指定绝对路径的文件名
	 * @return 如果删除成功true否则false
	 */
	public static boolean delete(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
			logger.error("File {} not found", strFileName);
			return false;
		}

		return fileDelete.delete();
	}

	/**
	 * @author xiaqiang
	 * @createtime 2014年7月11日 下午9:34:09
	 * @Decription 通过后缀名(带点的),返回人为分类类型
	 *
	 * @param ext
	 * @return
	 */
	public static String getAttachTypeByExt(String ext) {
		String attachType = "other";
		if (StringUtil.isNotEmpty(ext)) {
			ext = ext.toLowerCase();
			String[] docArray = { ".txt", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".vsd", ".wps", ".dps",
					".et" };
			String[] imgArray = { ".bmp", ".gif", ".icon", ".png", ".jpg", ".jpeg", ".tif" };
			String[] videoArray = { ".avi", ".mpg", ".mov", ".swf", ".flv", ".mkv", ".mp4", ".wav" };
			if (ArrayUtils.contains(docArray, ext)) {
				attachType = "doc";
			}
			if (ArrayUtils.contains(imgArray, ext)) {
				attachType = "img";
			}
			if (ArrayUtils.contains(videoArray, ext)) {
				attachType = "video";
			}
		}
		return attachType;
	}

	/**
	 * @author xiaqiang
	 * @createtime 2014年7月11日 下午9:34:09
	 * @Decription 通过后缀名(带点的),返回人为更加细分类型(用于前端图标判断类型)
	 *
	 * @param ext
	 * @return
	 */
	public static String getIconTypeByExt(String ext) {
		String iconType = "other";
		if (StringUtil.isNotEmpty(ext)) {
			ext = ext.toLowerCase();
			String[] wordArray = { ".doc", ".docx", ".wps" };
			String[] excelArray = { ".xls", ".xlsx", ".et" };
			String[] powerpointArray = { ".ppt", ".pptx", ".dps" };
			String[] pdfArray = { ".pdf" };
			String[] imgArray = { ".bmp", ".gif", ".icon", ".png", ".jpg", ".jpeg", ".tif" };
			String[] videoArray = { ".avi", ".mpg", ".mov", ".swf", ".flv", ".mkv", ".mp4", ".wav" };
			String[] audioArray = { ".mp3", ".wav", ".m4a" };
			String[] zipArray = { ".zip", ".rar", ".7z" };

			if (ArrayUtils.contains(wordArray, ext)) {
				iconType = "word";
			}
			if (ArrayUtils.contains(excelArray, ext)) {
				iconType = "excel";
			}
			if (ArrayUtils.contains(powerpointArray, ext)) {
				iconType = "powerpoint";
			}
			if (ArrayUtils.contains(pdfArray, ext)) {
				iconType = "pdf";
			}
			if (ArrayUtils.contains(imgArray, ext)) {
				iconType = "img";
			}
			if (ArrayUtils.contains(videoArray, ext)) {
				iconType = "video";
			}
			if (ArrayUtils.contains(audioArray, ext)) {
				iconType = "audio";
			}
			if (ArrayUtils.contains(zipArray, ext)) {
				iconType = "zip";
			}
		}

		return iconType;
	}

	public static void writeFile(String fileName, String content) {
		writeFile(fileName, content, StandardCharsets.UTF_8.toString());
	}

	public static void writeFile(String fileName, String content, String charset) {
		try {
			createFolder(fileName, true);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), charset));
			out.write(content);
			out.close();
		} catch (IOException e) {
			logger.error("writeFile exception:{}", e);
		}
	}

	public static void writeFile(String fileName, InputStream is) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] bs = new byte[512];
		int n = 0;
		while ((n = is.read(bs)) != -1) {
			fos.write(bs, 0, n);
		}
		is.close();
		fos.close();
	}

	public static String readFile(String fileName) {
		FileInputStream fin = null;
		try {
			File file = new File(fileName);
			String charset = getCharset(file);
			StringBuffer sb = new StringBuffer();
			fin = new FileInputStream(fileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(fin, charset));
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str + "\r\n");
			}
			in.close();
			return sb.toString();
		} catch (IOException e) {
			logger.error("readFile exception:{}", e);
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					logger.error("readFile FileInputStream close exception:{}", e);
				}
			}
		}
		return "";
	}

	public static boolean isDirExistFile(String dir) {
		boolean isExist = false;
		File fileDir = new File(dir);
		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			if ((files != null) && (files.length != 0)) {
				isExist = true;
			}
		}
		return isExist;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	public static String getCharset(File file) {
		String charset = "GBK";
		FileInputStream fin = null;
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			fin = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fin);
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if ((first3Bytes[0] == -1) && (first3Bytes[1] == -2)) {
				charset = "UTF-16LE";
				checked = true;
			} else if ((first3Bytes[0] == -2) && (first3Bytes[1] == -1)) {
				charset = "UTF-16BE";
				checked = true;
			} else if ((first3Bytes[0] == -17) && (first3Bytes[1] == -69) && (first3Bytes[2] == -65)) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();

			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 240) {
						break;
					}
					if ((128 <= read) && (read <= 191))
						break;
					if ((192 <= read) && (read <= 223)) {
						read = bis.read();
						if ((128 > read) || (read > 191)) {
							break;
						}

					} else if ((224 <= read) && (read <= 239)) {
						read = bis.read();
						if ((128 > read) || (read > 191))
							break;
						read = bis.read();
						if ((128 > read) || (read > 191))
							break;
						charset = "UTF-8";
						break;
					}

				}

			}

			bis.close();
		} catch (Exception e) {
			logger.error("readFile exception:{}", e);
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					logger.error("readFile FileInputStream close exception:{}", e);
				}
			}
		}
		return charset;
	}

	public static byte[] readByte(InputStream is) {
		try {
			byte[] r = new byte[is.available()];
			while ((is.read(r)) > 0)
				return r;
		} catch (Exception e) {
			logger.error("readByte exception:{}", e);
		}
		return null;
	}

	public static byte[] readByte(String fileName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] r = new byte[fis.available()];
			while ((fis.read(r)) > 0)
				fis.close();
			return r;
		} catch (Exception e) {
			logger.error("readByte exception:{}", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("readByte FileInputStream close exception:{}", e);
				}
			}
		}
		return null;
	}

	public static boolean writeByte(String fileName, byte[] b) {
		try {
			BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(fileName));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			logger.error("writeByte exception:{}", e);
		}
		return false;
	}

	@Deprecated
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static void serializeToFile(Object obj, String fileName) {
		try {
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(obj);
			out.close();
		} catch (IOException e) {
			logger.error("serializeToFile exception:{}", e);
		}
	}

	public static Object deserializeFromFile(String fileName) {
		try {
			File file = new File(fileName);
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (Exception e) {
			logger.error("deserializeFromFile exception:{}", e);
		}
		return null;
	}

	public static String inputStream2String(InputStream input, String charset) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(input, charset));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line + "\n");
		}
		return buffer.toString();
	}

	public static String inputStream2String(InputStream input) throws IOException {
		return inputStream2String(input, "utf-8");
	}

	public static File[] getFiles(String path) {
		File file = new File(path);
		return file.listFiles();
	}

	public static void createFileFolder(String path) {
		createFolder(path, true);
	}

	public static boolean createFolder(String path, boolean isFile) {
		if (isFile) {
			path = path.substring(0, path.lastIndexOf(File.separator));
		}
		File file = new File(path);
		boolean flag = false;
		if (!file.exists())
			flag = file.mkdirs();
		return flag;
	}

	public static void createFolder(String dirstr, String name) {
		dirstr = StringUtil.trimSufffix(dirstr, File.separator) + File.separator + name;
		File dir = new File(dirstr);
		dir.mkdir();
	}

	public static void renameFolder(String path, String newName) {
		File file = new File(path);
		if (file.exists())
			file.renameTo(new File(newName));
	}

	public static List<File> getDiretoryOnly(File dir) {
		List<File> dirs = new ArrayList<>();
		if ((dir != null) && (dir.exists()) && (dir.isDirectory())) {
			File[] files = dir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.isDirectory();
				}
			});
			for (int i = 0; i < files.length; i++) {
				dirs.add(files[i]);
			}
		}
		return dirs;
	}

	public List<File> getFileOnly(File dir) {
		List<File> dirs = new ArrayList<>();
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		for (int i = 0; i < files.length; i++) {
			dirs.add(files[i]);
		}
		return dirs;
	}

	public static boolean copyFile(InputStream in, OutputStream out) {
		try {
			byte[] buf = new byte[4096];
			int bytesRead;
			while ((bytesRead = in.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}

			out.flush();
		} catch (IOException e) {
			logger.error("copyFile InputStream close exception:{}", e);
			return false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("copyFile FileInputStream close exception:{}", e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("copyFile FileOutputStream close exception:{}", e);
				}
			}
		}
		return true;
	}

	public static void backupFile(String filePath) {
		String backupName = filePath + ".bak";
		File file = new File(backupName);
		if (file.exists()) {
			file.delete();
		}
		copyFile(filePath, backupName);
	}

	public static String getFileExt(File file) {
		if (file.isFile()) {
			return getFileExt(file.getName());
		}
		return "";
	}

	public static String getFileExt(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			return fileName.substring(pos + 1).toLowerCase();
		}
		return "";
	}

	public static String getFileExtByContentType(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}

	public static void copyDir(String fromDir, String toDir) throws IOException {
		new File(toDir).mkdirs();
		File[] file = new File(fromDir).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				String fromFile = file[i].getAbsolutePath();
				String toFile = toDir + "/" + file[i].getName();

				copyFile(fromFile, toFile);
			}
			if (file[i].isDirectory())
				copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/" + file[i].getName());
		}
	}

	private static void copyDirectiory(String fromDir, String toDir) throws IOException {
		new File(toDir).mkdirs();
		File[] file = new File(fromDir).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				String fromName = file[i].getAbsolutePath();
				String toFile = toDir + "/" + file[i].getName();
				copyFile(fromName, toFile);
			}
			if (file[i].isDirectory())
				copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/" + file[i].getName());
		}
	}

	public static String getFileSize(File file) throws IOException {
		if (file.isFile()) {
			FileInputStream fis = new FileInputStream(file);
			int size = fis.available();
			fis.close();
			return convertFileSize(size);
		}
		return "";
	}

	public static String convertFileSize(double size) {
		DecimalFormat df = new DecimalFormat("0.0");
		if (size > 1073741824.0D) {
			double ss = size / 1073741824.0D;
			return df.format(ss) + "MB";
		}
		if (size > 1048576.0D) {
			double ss = size / 1048576.0D;
			return df.format(ss) + "MB";
		}
		if (size > 1024.0D) {
			double ss = size / 1024.0D;
			return df.format(ss) + "KB";
		}
		return size + "B";
	}

	public static String convertFileSize(long size) {
		double lsize = (long) size;
		return convertFileSize(lsize);
	}

	/**
	 * @author xiaqiang
	 * @createtime 2014年7月14日 下午6:18:22
	 * @Decription 下载文件方法(通过本地文件路径)
	 *
	 * @param request
	 * @param response
	 * @param fullPath
	 *            下载路径
	 * @param fileName
	 *            下载文件名
	 * @throws IOException
	 */
	public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fullPath,
			String fileName) throws IOException {
		OutputStream outp = null;
		String finalPath = fullPath;
		File file = new File(finalPath);
		if (file.exists()) {
			response.reset();
			response.setContentType("APPLICATION/OCTET-STREAM");
			String filedisplay = fileName;
			String agent = request.getHeader("USER-AGENT");
			response.addHeader("Content-Length", file.length() + "");
			if ((agent != null) && (agent.indexOf("MSIE") == -1)) {
				// String enableFileName = "=?UTF-8?B?" + new
				// String(Base64.getBase64(filedisplay)) + "?=";
				// response.setHeader("Content-Disposition",
				// "attachment; filename=" + enableFileName);
				filedisplay = URLEncoder.encode(filedisplay, "utf-8");
				response.addHeader("Content-Disposition", "attachment; filename=" + filedisplay);
			} else {
				filedisplay = URLEncoder.encode(filedisplay, "utf-8");
				response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			}
		} else {
			// finalPath=ApplicationContextUtil.getRealPath("/basic/img/avatars/avatar_80.png");
			// file=new File(finalPath);
			// outp.write("文件不存在!".getBytes("utf-8"));
		}
		Long length = file.length();
		response.addHeader("Content-Length", length.toString());
		FileInputStream in = null;
		try {
			outp = response.getOutputStream();
			in = new FileInputStream(finalPath);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			logger.error("deserializeFromFile exception:{}", e);
		} finally {
			if (outp != null) {
				outp.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * @author xiaqiang
	 * @createtime 2014年7月14日 下午6:18:22
	 * @Decription 下载文件方法(通过输入流)
	 *
	 * @param request
	 * @param response
	 * @param is
	 *            输入流
	 * @param fileName
	 *            下载文件名
	 * @throws IOException
	 */
	public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, InputStream is,
			String fileName) throws IOException {
		OutputStream outp = null;
		response.setContentType("APPLICATION/OCTET-STREAM");
		String filedisplay = fileName;
		String agent = request.getHeader("USER-AGENT");

		if ((agent != null) && (agent.indexOf("MSIE") == -1)) {
			String enableFileName = "=?UTF-8?B?" + new String(Base64.encodeBase64(filedisplay.getBytes("utf-8")))
					+ "?=";
			response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
		} else {
			filedisplay = URLEncoder.encode(filedisplay, "utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
		}
		try {
			outp = response.getOutputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = is.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			logger.error("downLoadFile exception:{}", e);
		} finally {
			if (is != null) {
				is.close();
				is = null;
			}
			if (outp != null) {
				outp.close();
				outp = null;
				response.flushBuffer();
			}
		}
	}

	public static String getParentDir(String baseDir, String currentFile) {
		File f = new File(currentFile);
		String parentPath = f.getParent();
		String path = parentPath.replace(baseDir, "");
		return path.replace(File.separator, "/");
	}

	// public static String getClassesPath() {
	// String path =
	// StringUtil.trimSufffix(ApplicationContextUtil.getRealPath("/"),
	// File.separator)
	// + "\\WEB-INF\\classes\\".replace("\\", File.separator);
	// // return ApplicationContextUtil.getClasspath();
	// return path;
	// }

	// public static String getRootPath() {
	// String rootPath =
	// StringUtil.trimSufffix(ApplicationContextUtil.getRealPath("/"),
	// File.separator)
	// + File.separator;
	// return rootPath;
	// }

	public static List<Class<?>> getAllClassesByInterface(Class<?> interfaceClass, boolean samePackage)
			throws IOException, ClassNotFoundException, IllegalStateException {
		if (!interfaceClass.isInterface()) {
			throw new IllegalStateException("Class not a interface.");
		}

		ClassLoader $loader = interfaceClass.getClassLoader();

		String packageName = samePackage ? interfaceClass.getPackage().getName() : "/";
		return findClasses(interfaceClass, $loader, packageName);
	}

	private static List<Class<?>> findClasses(Class<?> interfaceClass, ClassLoader loader, String packageName)
			throws IOException, ClassNotFoundException {
		List<Class<?>> allClasses = new ArrayList<>();

		String packagePath = packageName.replace(".", "/");
		if (!packagePath.equals("/")) {
			Enumeration<URL> resources = loader.getResources(packagePath);
			while (resources.hasMoreElements()) {
				URL $url = (URL) resources.nextElement();
				allClasses.addAll(findResources(interfaceClass, new File($url.getFile()), packageName));
			}
		} else {
			String path = loader.getResource("").getPath();
			allClasses.addAll(findResources(interfaceClass, new File(path), packageName));
		}
		return allClasses;
	}

	private static List<Class<?>> findResources(Class<?> interfaceClass, File directory, String packageName)
			throws ClassNotFoundException {
		List<Class<?>> $results = new ArrayList<>();
		if (!directory.exists())
			return Collections.EMPTY_LIST;
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				if (!file.getName().contains(".")) {
					if (!packageName.equals("/"))
						$results.addAll(findResources(interfaceClass, file, packageName + "." + file.getName()));
					else
						$results.addAll(findResources(interfaceClass, file, file.getName()));
				}
			} else if (file.getName().endsWith(".class")) {
				Class clazz = null;
				if (!packageName.equals("/"))
					clazz = Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6));
				else {
					clazz = Class.forName(file.getName().substring(0, file.getName().length() - 6));
				}
				if ((interfaceClass.isAssignableFrom(clazz)) && (!interfaceClass.equals(clazz))) {
					$results.add(clazz);
				}
			}
		}
		return $results;
	}

	public static Object cloneObject(Object obj) throws Exception {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(obj);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);

		return in.readObject();
	}

	/**
	 * @author xiaqiang
	 * @createtime 2014年9月2日 下午2:26:01
	 * @Decription 将输入流,用response输出为图片类型
	 *
	 * @param request
	 * @param response
	 * @param is
	 *            输入流
	 */
	public static void viewImg(HttpServletRequest request, HttpServletResponse response, InputStream is) {
		OutputStream outp = null;
		response.setContentType("multipart/form-data");
		try {
			outp = response.getOutputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = is.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			logger.error("viewImg exception:{}", e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
					response.flushBuffer();
				} catch (IOException e) {
					logger.error("viewImg OutputStream close exception:{}", e);
				}
			}
		}
	}

	/**
	 * @author xiaqiang
	 * @createtime 2014年11月24日 上午9:15:17
	 * @Decription 下载workbook
	 *
	 * @param request
	 * @param response
	 * @param workbook
	 * @param fileName
	 * @throws IOException
	 */
	public static void downloadWorkBook(HttpServletRequest request, HttpServletResponse response, Workbook workbook,
			String fileName) throws IOException {
		OutputStream outp = null;
		response.setContentType("APPLICATION/OCTET-STREAM");
		String filedisplay = fileName;
		String agent = request.getHeader("USER-AGENT");

		if ((agent != null) && (agent.indexOf("MSIE") == -1)) {
			String enableFileName = "=?UTF-8?B?" + new String(Base64.encodeBase64(filedisplay.getBytes("utf-8")))
					+ "?=";
			response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
		} else {
			filedisplay = URLEncoder.encode(filedisplay, "utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
		}
		outp = response.getOutputStream();
		workbook.write(outp);
		outp.flush();
		outp.close();
		outp = null;
		response.flushBuffer();
	}

	/**
	 * @author biny
	 * @Decription 级联删除目录下的文件
	 *
	 */
	@Deprecated
	public static boolean deleteFolder(File dir) {
		boolean result = false;
		if (dir.exists()) {
			File filelist[] = dir.listFiles();
			int listlen = filelist.length;
			for (int i = 0; i < listlen; i++) {
				if (filelist[i].isDirectory()) {
					deleteFolder(filelist[i]);
				} else {
					filelist[i].delete();
				}
			}
			dir.delete();// 删除当前目录
			result = true;
		} else {
			result = true;
		}
		return result;
	}

	/**
	 * 根据response调取文件名
	 * 
	 * @param response
	 * @return
	 */
	// public static String getFileName(HttpResponse response) {
	// Header contentHeader = response.getFirstHeader("Content-Disposition");
	// String filename = null;
	// if (contentHeader != null) {
	// HeaderElement[] values = contentHeader.getElements();
	// if (values.length == 1) {
	// NameValuePair param = values[0].getParameterByName("filename");
	// if (param != null) {
	// try {
	// // filename = new
	// // String(param.getValue().toString().getBytes(),
	// // "utf-8");
	// // filename=URLDecoder.decode(param.getValue(),"utf-8");
	// filename = param.getValue();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// return filename;
	// }

	/**
	 * 根据后缀名(如.jpg) 来获得 ContentType(如image/jpeg)
	 * 
	 * @param fileExtension
	 * @return
	 */
	public static String getFileContentTypeByExtension(String fileExtension) {
		String contentType = "";
		if (".jpg".equalsIgnoreCase(fileExtension) || ".jpeg".equalsIgnoreCase(fileExtension))
			contentType = "image/jpeg";
		else if (".mp3".equalsIgnoreCase(fileExtension))
			contentType = "audio/mpeg";
		else if (".amr".equalsIgnoreCase(fileExtension))
			contentType = "audio/amr";
		else if (".mp4".equalsIgnoreCase(fileExtension))
			contentType = "video/mp4";
		else if (".html".equalsIgnoreCase(fileExtension) || ".htm".equals(fileExtension))
			contentType = "text/html";
		return contentType;
	}

	/**
	 * 获得时间+随机数产生的随机文件名
	 * 
	 * @return
	 */
	public static String getDateRandomFileName() {
		String datePart = DateUtils.format(new Date(), "yyyyMMddHHmmss");
		String randomPart = RandomStringUtils.randomNumeric(6);
		return datePart + randomPart;
	}
}
